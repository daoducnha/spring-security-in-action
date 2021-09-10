package com.example.chap_10.config;

import com.example.chap_10.filter.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StaticKeyAuthenticationFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(
//                        new RequestValidationFilter(),
//                        BasicAuthenticationFilter.class
//                )
//                .addFilterAfter(
//                        new AuthenticationLoggingFilter()
//                        , BasicAuthenticationFilter.class
//                )
//                .authorizeRequests()
//                .anyRequest().permitAll();

        http.addFilterAt(filter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
