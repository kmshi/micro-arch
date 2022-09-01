package com.haxejs.microservices.core.user.model.unionkey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class RolePermKey implements Serializable{
    private static final long serialVersionUID = 6850974328279713855L;
    
    @Column(name = "perm_id")
    private Long permId;
    
    @Column(name = "role_id")
    private Long roleId;
}
