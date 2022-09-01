package com.haxejs.microservices.core.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haxejs.microservices.core.user.model.UserRole;
import com.haxejs.microservices.core.user.model.unionkey.UserRoleKey;

public interface UserRoleDao extends JpaRepository<UserRole, UserRoleKey>, JpaSpecificationExecutor<UserRole>{
    
}
