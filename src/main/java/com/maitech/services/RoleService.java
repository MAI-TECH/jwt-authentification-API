package com.maitech.services;

import com.maitech.models.RoleModel;
import com.maitech.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleModel> findAllRoles() {
        return roleRepository.findAll();
    }

    public RoleModel findRoleById(String idRoleModel) {
        RoleModel roleModel = roleRepository.findById(idRoleModel).get();

        return (roleModel != null) ? roleModel : null;
    }

    public RoleModel findRoleByName(String name) {
        RoleModel roleModel = roleRepository.findByName(name);

        return (roleModel != null) ? roleModel : null;
    }

    public RoleModel findRoleByCode(String code) {
        RoleModel roleModel = roleRepository.findByCode(code);

        return (roleModel != null) ? roleModel : null;
    }

    public RoleModel saveRole(RoleModel roleModel) throws Exception {
        RoleModel role = new RoleModel();
        List<String> errorList = new ArrayList<>();

        if (roleModel.getIdRoleModel() != null)
            role.setIdRoleModel(roleModel.getIdRoleModel());

        if (roleModel.getName() == null)
            errorList.add("The name of role could not empty");
        role.setName(roleModel.getName());

        if (roleModel.getCode() == null)
            errorList.add("The code of role could not empty");
        role.setCode(roleModel.getCode());

        if (!errorList.isEmpty()) {
            String errorMessage = "";
            for (String error : errorList)
                errorMessage += (error + "\n");
            throw new Exception(errorMessage);
        }
        role.setDescription(roleModel.getDescription());

        roleRepository.save(role);
        return role;
    }

    public boolean deleteRoleById(String idRoleModel) {
        RoleModel roleModel = roleRepository.findById(idRoleModel).get();

        if (roleModel != null) {
            roleRepository.delete(roleModel);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteRoleByName(String name) {
        RoleModel roleModel = roleRepository.findByName(name);

        if (roleModel != null) {
            roleRepository.delete(roleModel);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteRoleByCode(String code) {
        RoleModel roleModel = roleRepository.findByCode(code);

        if (roleModel != null) {
            roleRepository.delete(roleModel);
            return true;
        }
        else {
            return false;
        }
    }
}
