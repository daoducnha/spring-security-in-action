package com.example.chap_11_business_server_2.security.provider;

import com.example.chap_11_business_server_2.proxy.AuthenticationServerProxy;
import com.example.chap_11_business_server_2.security.authentication.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());

        boolean isValid = proxy.sendOtp(username, code);
        if(isValid){
            return new UsernamePasswordAuthenticationToken(username, code);
        }else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
