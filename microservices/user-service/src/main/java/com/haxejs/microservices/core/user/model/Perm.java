package com.haxejs.microservices.core.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
@Table(name="perms")
public class Perm {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private String name;
}