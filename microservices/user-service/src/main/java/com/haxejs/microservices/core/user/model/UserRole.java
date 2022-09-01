package com.haxejs.microservices.core.user.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import com.haxejs.microservices.core.user.model.unionkey.UserRoleKey;

@Data
@Entity
@Table(name = "users_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleKey id;
}
