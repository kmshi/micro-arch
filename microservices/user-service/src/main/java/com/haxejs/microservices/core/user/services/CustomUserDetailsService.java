package com.haxejs.microservices.core.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.haxejs.microservices.core.user.model.User;
import com.haxejs.microservices.core.user.model.Role;
import com.haxejs.microservices.core.user.dto.UserPrincipal;
import com.haxejs.microservices.core.user.model.Perm;
import com.haxejs.microservices.core.user.repository.PermDao;
import com.haxejs.microservices.core.user.repository.RoleDao;
import com.haxejs.microservices.core.user.repository.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermDao permDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsernameOrEmailOrPhone(username, username, username).orElseThrow(()->new UsernameNotFoundException("Can not find username/phone/email:"+username));
        List<Role> rObjs = roleDao.selectByUserId(user.getId());
        List<Long> rIds = rObjs.stream().map(Role::getId).toList();
        List<String> roles = rObjs.stream().map(Role::getName).toList();
        List<String> perms = permDao.selectByRoleIdList(rIds).stream().map(Perm::getName).toList();
        return UserPrincipal.builder()
                    .id(user.getId()) 
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .enabled(user.isEnabled())
                    .roles(roles)
                    .perms(perms)
                    .build();
    }
}

