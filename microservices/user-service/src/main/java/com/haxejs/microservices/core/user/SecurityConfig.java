package com.haxejs.microservices.core.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(HttpMethod.GET,
            "/actuator/**", 
            "/h2-console",
            "/oauth2/jwks"
        ).antMatchers(HttpMethod.POST, "/oauth2/login");
    }

    // @Bean
	// public JwtDecoder jwtDecoder() throws JwtException{
    //     RSAPublicKey key;
    //     try{
    //         RSAKey rsaJWK = (RSAKey)jwkSet.getKeys().get(0);
    //         key = (RSAPublicKey)rsaJWK.toPublicKey();
    //     }catch(JOSEException ex){
    //         throw new JwtException(ex.getMessage());
    //     }
        
        
	// 	return NimbusJwtDecoder.withPublicKey(key).build();
	// }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors()
                // ?????? CSRF
                .and().csrf().disable()
                // ???????????????????????????????????? AuthController#login
                .formLogin().disable()
                .httpBasic().disable()

                // ????????????
                .authorizeRequests()
                // ?????????????????????????????????
                .anyRequest()
                .authenticated()

                // ???????????????????????????????????? AuthController#logout
                .and().logout().disable()
                // Session ??????
                .sessionManagement()
                // ???????????????JWT????????????????????????Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer().jwt().jwkSetUri(jwkSetUri);

                // ????????????
                //.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        // ??????????????? JWT ?????????
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
