package com.maitech.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "users")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class UserModel implements Serializable {

    @Id
    private String idUserModel = UUID.randomUUID().toString();

    private String lastName;

    private String firstName;

    private String gender;

    @Indexed(name = "phone", unique = true)
    private int phone;

    @Indexed(name = "email", unique = true)
    private String email;

    private byte[] avatar;

    private String format;

    @Indexed(name = "username", unique = true)
    private String username;

    private String password;

    @DBRef
    private RoleModel roleModel;
}
