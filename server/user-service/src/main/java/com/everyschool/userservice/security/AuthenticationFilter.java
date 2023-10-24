package com.everyschool.userservice.security;

import com.everyschool.userservice.api.controller.user.request.LoginRequest;
import com.everyschool.userservice.api.service.user.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AccountService accountService;
    private final Environment env;
    private final Key key;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AccountService accountService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.accountService = accountService;
        this.env = env;
        byte[] keyBytes = Decoders.BASE64.decode(this.env.getProperty("token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String email = ((User) authResult.getPrincipal()).getUsername();
        com.everyschool.userservice.domain.user.User user = accountService.getUserDetailsByEmail(email);

        String token = Jwts.builder()
            .setSubject(user.getUserKey())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        response.addHeader("token", token);
        response.addHeader("userKey", user.getUserKey());
        //servlet
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
