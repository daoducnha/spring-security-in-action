package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

//    @Autowired
//    private CustomAuthenticationProvider authenticationProvider;


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
//        http.authorizeRequests()
//                .anyRequest().authenticated();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
//        var userDetailService = new InMemoryUserDetailsManager();
//
//        var user = User.withUsername("john")
//                .password(new BCryptPasswordEncoder().encode("1234567"))
//                .authorities("read")
//                .build();
//
//        userDetailService.createUser(user);
//
//        auth.userDetailsService(userDetailService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("john")
                .password("123456")
                .authorities("read")
                .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }
}
