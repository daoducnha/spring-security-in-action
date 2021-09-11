package com.example.chap_11_business_server_2.config;

import com.example.chap_11_business_server_2.security.authentication.UsernamePasswordAuthentication;
import com.example.chap_11_business_server_2.security.filter.AuthenticationFilter;
import com.example.chap_11_business_server_2.security.provider.JwtAuthenticationProvider;
import com.example.chap_11_business_server_2.security.provider.OtpAuthenticationProvider;
import com.example.chap_11_business_server_2.security.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(authenticationFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
