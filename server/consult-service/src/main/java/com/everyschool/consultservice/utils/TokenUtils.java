package com.everyschool.consultservice.utils;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class TokenUtils {

    private final Environment env;

    public String getUserKey() {
        String token = getTokenByHeader();

        return getSubject(token);
    }

    private String getTokenByHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return authorizationHeader.replace("Bearer", "");
    }

    private String getSubject(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(env.getProperty("token.secret"))
            .build().parseClaimsJws(token).getBody()
            .getSubject();
    }
}
