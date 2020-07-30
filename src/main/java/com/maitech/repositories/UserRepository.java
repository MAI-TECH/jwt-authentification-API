package com.maitech.repositories;

import com.maitech.models.RoleModel;
import com.maitech.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {
    public UserModel findByPhone(int phone);
    public UserModel findByEmail(String email);
    public UserModel findByUsername(String username);
    public List<UserModel> findByRoleModel(RoleModel roleModel);
}
