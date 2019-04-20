package com.glacierluo.platform.repository;

import com.glacierluo.platform.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole,Long> {
    UserRole findByuserId(Long id);

}
