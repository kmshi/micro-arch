package com.haxejs.microservices.core.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haxejs.microservices.core.user.model.RolePerm;
import com.haxejs.microservices.core.user.model.unionkey.RolePermKey;

public interface RolePermDao extends JpaRepository<RolePerm, RolePermKey>, JpaSpecificationExecutor<RolePerm>{
    
}
