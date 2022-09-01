package com.haxejs.microservices.core.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haxejs.microservices.core.user.model.User;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);
    List<User> findByUsernameIn(List<String> usernameList);
}
