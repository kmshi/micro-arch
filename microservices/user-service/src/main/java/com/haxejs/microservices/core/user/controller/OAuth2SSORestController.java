package com.haxejs.microservices.core.user.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;

import com.haxejs.microservices.core.user.dto.LoginRequest;
import com.haxejs.microservices.core.user.dto.UserPrincipal;
import com.haxejs.microservices.core.user.services.CustomUserDetailsService;

@RestController
public class OAuth2SSORestController {
    @Value("${app.auth-server}")         String authServer;

    @Autowired
    private JWKSet jwkSet;

    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }

    @GetMapping("/oauth2/userinfo")
    public UserPrincipal userinfo() throws UsernameNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt)authentication.getPrincipal();
        UserPrincipal user = (UserPrincipal)userDetailsService.loadUserByUsername(jwt.getSubject());
        return user;
    }

    @GetMapping("/oauth2/refresh")
    public Map<String, Object> refresh() throws JOSEException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt)authentication.getPrincipal();
        UserPrincipal user = (UserPrincipal)userDetailsService.loadUserByUsername(jwt.getSubject());

        String token = createToken(user);

        return new JWTClaimsSet.Builder()
            .claim("access_token", token)
            .claim("expires_in", 3600)
            .claim("token_type", "Bearer")
            .build().toJSONObject();
    }

    @PostMapping("/oauth2/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) throws JOSEException{

        UserPrincipal user = (UserPrincipal)userDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmailOrPhone());
        //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //boolean isMatched = encoder.matches(loginRequest.getPassword(),user.getPassword());

        CharSequence rawPassword = loginRequest.getPassword();
        boolean isMatched = encoder.matches(rawPassword,user.getPassword());
        if (!isMatched) throw new JOSEException("password is not matched");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));
        
        String token = createToken(user);

        return new JWTClaimsSet.Builder()
            .claim("access_token", token)
            .claim("expires_in", 3600)
            .claim("token_type", "Bearer")
            .build().toJSONObject();
    }

    private String createToken(UserPrincipal user) throws JOSEException{
        // Prepare JWS object
        Date now = new Date();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issuer("http://"+authServer)
            .issueTime(now)
            .expirationTime(new Date(now.getTime()+3600*1000))
            .claim("scope", user.getPerms())
            .build();

        RSAKey rsaJWK = (RSAKey)jwkSet.getKeys().get(0);
        JWSObject jwsObject = new JWSObject(
            new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
            new Payload(claims.toJSONObject())
        );

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(rsaJWK);
        // Compute the RSA signature
        jwsObject.sign(signer);
        
        String token = jwsObject.serialize();
        return token;
    }
}
