package com.maitech.dtos;

import com.maitech.models.RoleModel;
import com.maitech.models.UserModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data @NoArgsConstructor @ToString
public class RoleDto implements Serializable {

    private String idRole;

    private String name;

    private String code;

    private String description;

    public RoleDto fromRoleModel(RoleModel roleModel) {
        this.idRole = roleModel.getIdRoleModel();
        this.name = roleModel.getName();
        this.code = roleModel.getCode();
        this.description = roleModel.getDescription();

        return this;
    }

    public RoleModel toRoleModel() {
        RoleModel roleModel = new RoleModel();

        roleModel.setName(name);
        roleModel.setCode(code);
        roleModel.setDescription(description);

        return roleModel;
    }
}
