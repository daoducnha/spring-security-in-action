package com.example.chap_4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", bCryptPasswordEncoder());
        encoders.put("scrypt", sCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        UserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        UserDetails userNoOp = User.withUsername("User_noop")
                .password("12345")
                .passwordEncoder(s -> "{noop}" + s)
                .authorities("READ")
                .disabled(false)
                .build();

        UserDetails userBcrypt = User.withUsername("User_Bcrypt")
                .password(bCryptPasswordEncoder().encode("12345"))
                .passwordEncoder(s -> "{bcrypt}" + s)
                .authorities("READ")
                .disabled(false)
                .build();

        UserDetails userScrypt = User.withUsername("User_Scrypt")
                .password(sCryptPasswordEncoder().encode("123456"))
                .passwordEncoder(s -> "{scrypt}" + s)
                .authorities("READ")
                .disabled(false)
                .build();

        userDetailsManager.createUser(userNoOp);
        userDetailsManager.createUser(userBcrypt);
        userDetailsManager.createUser(userScrypt);

        return userDetailsManager;

    }
}
