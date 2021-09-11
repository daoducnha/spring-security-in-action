package com.example.chap_11_business_server_2.security.provider;

import com.example.chap_11_business_server_2.security.authentication.JwtAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = String.valueOf(authentication.getCredentials());

        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        String username = String.valueOf(claims.get("username"));

        if(username!= null){
            GrantedAuthority authority = new SimpleGrantedAuthority("user");
            return new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
