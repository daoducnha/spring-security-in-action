package com.example.chap_7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
//                .authorities("READ")
//                .authorities("ROLE_ADMIN")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
//                .authorities("WRITE")
//                .authorities("ROLE_ADMIN")
                .roles("ADMIN")
                .build();

        var user3 = User.withUsername("alex")
                .password("12345")
//                .authorities("READ", "WRITE", "DELETE")
//                .authorities("ROLE_MANAGER")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);
        manager.createUser(user3);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        String expression = "hasAuthority('READ') and !hasAuthority('DELETE')";
        http.authorizeRequests()
                .anyRequest()
//                .hasAnyAuthority("READ", "WRITE");
//                .access(expression);
//                .hasRole("ADMIN");
        .denyAll();
    }
}
