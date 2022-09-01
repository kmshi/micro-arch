package com.haxejs.microservices.core.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import com.haxejs.microservices.core.user.model.Perm;
import com.haxejs.microservices.core.user.model.Role;
import com.haxejs.microservices.core.user.model.User;
import com.haxejs.microservices.core.user.repository.PermDao;
import com.haxejs.microservices.core.user.repository.RoleDao;
import com.haxejs.microservices.core.user.repository.UserDao;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.*;

@SpringBootTest
class UserServiceApplicationTests {
	@Autowired
	UserDao userDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	PermDao permDao;

	@Test
	void contextLoads() {
		User user = userDao.findByUsernameOrEmailOrPhone("admin","admin","admin").orElse(null);
		List<Long> rIds = roleDao.selectByUserId(user.getId()).stream().map(Role::getId).toList();
		System.out.println(rIds);
		List<String> perms = permDao.selectByRoleIdList(rIds).stream().map(Perm::getName).toList();
		System.out.println(perms);
	}

	@Test
	void testJwks(){
		try{
			RSAKey rsaJWK = new RSAKeyGenerator(2048)
				.keyID("123")
				.generate();
			JWKSet jwkSet = new JWKSet(rsaJWK);
			System.out.println(jwkSet);
			RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
		}catch(Exception e){

		}
		
	}
}
