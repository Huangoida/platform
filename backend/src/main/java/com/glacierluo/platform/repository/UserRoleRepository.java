package com.glacierluo.platform.repository;

import com.glacierluo.platform.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByuserId(Long id);
}
