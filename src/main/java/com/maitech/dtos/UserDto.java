package com.maitech.dtos;

import com.maitech.models.RoleModel;
import com.maitech.models.UserModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data @NoArgsConstructor @ToString
public class UserDto implements Serializable {

    private String idUser;

    private String lastName;

    private String firstName;

    private String gender;

    private int phone;

    private String email;

    private String username;

    private String password;

    private String role;

    public UserDto fromUserModel(UserModel userModel) {
        this.idUser = userModel.getIdUserModel();
        this.lastName = userModel.getLastName();
        this.firstName = userModel.getFirstName();
        this.gender = userModel.getGender();
        this.phone = userModel.getPhone();
        this.email = userModel.getEmail();
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.role = userModel.getRoleModel().getCode();

        return this;
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();

        userModel.setIdUserModel(idUser);
        userModel.setLastName(lastName);
        userModel.setFirstName(firstName);
        userModel.setGender(gender);
        userModel.setPhone(phone);
        userModel.setEmail(email);
        userModel.setUsername(username);
        userModel.setPassword(password);
        userModel.setRoleModel(new RoleModel(role));

        return userModel;
    }
}
