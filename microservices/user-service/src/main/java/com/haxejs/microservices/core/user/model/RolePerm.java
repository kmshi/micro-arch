package com.haxejs.microservices.core.user.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import com.haxejs.microservices.core.user.model.unionkey.RolePermKey;

@Data
@Entity
@Table(name = "roles_perms")
public class RolePerm {
    @EmbeddedId
    private RolePermKey id;
}
