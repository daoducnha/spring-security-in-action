package com.example.chap_15_asymmetric_key_rs_v3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableAuthorizationServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
}
