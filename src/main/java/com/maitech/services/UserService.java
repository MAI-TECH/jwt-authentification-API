package com.maitech.services;

import com.maitech.models.UserModel;
import com.maitech.repositories.RoleRepository;
import com.maitech.repositories.UserRepository;
import com.maitech.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Validation validation;

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public UserModel findUserById(String idUserModel) {
        UserModel userModel = userRepository.findById(idUserModel).get();

        return (userModel != null) ? userModel : null;
    }

    public UserModel findUserByPhone(int phone) {
        UserModel userModel = userRepository.findByPhone(phone);

        return (userModel != null) ? userModel : null;
    }

    public UserModel findUserByEmail(String email) {
        UserModel userModel = userRepository.findByEmail(email);

        return (userModel != null) ? userModel : null;
    }

    public UserModel findUserByUsername(String username) {
        UserModel userModel = userRepository.findByUsername(username);

        return (userModel != null) ? userModel : null;
    }

    public UserModel saveUser(@org.jetbrains.annotations.NotNull UserModel userModel) throws Exception {
        UserModel user = new UserModel();
        List<String> errorList = new ArrayList<>();

        if (userModel.getIdUserModel() != null)
            user.setIdUserModel(userModel.getIdUserModel());

        if (userModel.getLastName() == null || userModel.getLastName().length() < 5)
            errorList.add("The lastName of user could not empty");
        user.setLastName(userModel.getFirstName());

        if (userModel.getFirstName() == null || userModel.getFirstName().length() < 3)
            errorList.add("The firstName of user could not empty");
        user.setFirstName(userModel.getFirstName());

        if (!userModel.getGender().toUpperCase().equals("HOMME") && !userModel.getGender().toUpperCase().equals("FEMME"))
            errorList.add("Unexpected value : " + user.getGender());
        user.setGender(userModel.getGender().toUpperCase());

        if (!validation.isValidPhone(""+ userModel.getPhone() + ""))
            errorList.add("ERROR : Phone is not valid");
        user.setPhone(userModel.getPhone());

        if (!validation.isValidEmail(userModel.getEmail()))
            errorList.add("Error : Email is not valid");
        user.setEmail(userModel.getEmail());

        if (userModel.getUsername().isEmpty())
            errorList.add("ERROR : Username is not valid");
        user.setUsername(userModel.getUsername());

        if (!validation.isValidPassword(userModel.getPassword()))
            errorList.add("Error");
        user.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));

        if (roleRepository.findByName(userModel.getRoleModel().getName()) == null)
            errorList.add("Unexpected Value : " + userModel.getRoleModel().getName());
        user.setRoleModel(roleRepository.findByName(userModel.getRoleModel().getName()));

        if (!errorList.isEmpty()) {
            String errorMessage = "";
            for (String error : errorList)
                errorMessage += (error + "\n");
            throw new Exception(errorMessage);
        }

        userRepository.save(user);
        return user;
    }

    public boolean deleteUserById(String idUserModel) {
        UserModel userModel = userRepository.findById(idUserModel).get();

        if (userModel != null) {
            userRepository.delete(userModel);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteUserByPhone(int phone) {
        UserModel userModel = userRepository.findByPhone(phone);

        if (userModel != null) {
            userRepository.delete(userModel);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteUserByEmail(String email) {
        UserModel userModel = userRepository.findByEmail(email);

        if (userModel != null) {
            userRepository.delete(userModel);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteUserByUsername(String username) {
        UserModel userModel = userRepository.findByUsername(username);

        if (userModel != null) {
            userRepository.deleteById(username);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean uploadAvatar(String idUserModel, MultipartFile file) throws IOException, Exception {
        UserModel userModel = userRepository.findById(idUserModel).get();

        if (userModel == null) {
            throw new Exception("ERROR : User is not found");
        }

        userModel.setAvatar(file.getBytes());
        userModel.setFormat(file.getContentType());

        userRepository.save(userModel);
        return true;
    }
}
