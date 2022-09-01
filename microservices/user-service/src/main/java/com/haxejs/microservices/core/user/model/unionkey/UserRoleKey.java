package com.haxejs.microservices.core.user.model.unionkey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserRoleKey implements Serializable{
    private static final long serialVersionUID = 5633412144183654743L;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "role_id")
    private Long roleId;
}
