package com.maitech.repositories;

import com.maitech.models.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<RoleModel, String> {
    public RoleModel findByName(String name);
    public RoleModel findByCode(String code);
}
