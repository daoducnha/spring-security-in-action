package com.example.chap_8.config;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john").password("12345").roles("ADMIN").build();
        var user2 = User.withUsername("jane").password("12345").roles("MANAGER").build();
        var user3 = User.withUsername("tom").password("12345").authorities("read").build();
        var user4 = User.withUsername("jerry").password("12345").authorities("read", "premium").build();

        manager.createUser(user1);
        manager.createUser(user2);
        manager.createUser(user3);
        manager.createUser(user4);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

//        http.authorizeRequests()
//                .mvcMatchers("/hello").hasRole("ADMIN")
//                .mvcMatchers("/ciao").hasRole("MANAGER")
//                .mvcMatchers(HttpMethod.GET, "/a")
//                    .authenticated()
//                .mvcMatchers(HttpMethod.POST, "/a")
//                    .permitAll()
//                .mvcMatchers("/product/{code:^[0-9]*$}")
//                    .permitAll()
//                .anyRequest()
//                    .denyAll();

        http.authorizeRequests()
                .antMatchers("/hello").authenticated()
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*")
                .authenticated()
                .anyRequest()
                .hasAnyAuthority("premium");

        http.csrf().disable();
    }
}
