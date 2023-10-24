package com.everyschool.userservice.security;

import com.everyschool.userservice.api.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(accountService).passwordEncoder(bCryptPasswordEncoder);
        authenticationManager = authenticationManagerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, accountService, env);

        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers("/error/**", "/actuator/**", "/h2-console/**").permitAll()
            .antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
            .and()
            .authenticationManager(authenticationManager)
            .addFilter(authenticationFilter);

        http.headers().frameOptions().disable();

        return http.build();
    }
}

