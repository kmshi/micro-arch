package com.haxejs.microservices.core.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haxejs.microservices.core.user.model.Role;

public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>{
    @Query(value = "SELECT roles.* FROM roles,users,users_roles WHERE users.id = users_roles.user_id AND roles.id = users_roles.role_id AND users.id = :userId", nativeQuery = true)
    List<Role> selectByUserId(@Param("userId") Long userId);
}
