package com.maitech.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "roles")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class RoleModel implements Serializable {

    @Id
    private String idRoleModel = UUID.randomUUID().toString();

    @Indexed(name = "name", unique = true)
    private String name;

    @Indexed(name = "code", unique = true)
    private String code;

    private String description;
}
