package com.haxejs.microservices.core.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haxejs.microservices.core.user.model.Perm;

public interface PermDao extends JpaRepository<Perm, Long>, JpaSpecificationExecutor<Perm>{
    @Query(value = "SELECT DISTINCT perms.* FROM perms,roles,roles_perms WHERE roles.id = roles_perms.role_id AND perms.id = roles_perms.perm_id AND roles.id IN (:ids)", nativeQuery = true)
    List<Perm> selectByRoleIdList(@Param("ids") List<Long> ids);
}
