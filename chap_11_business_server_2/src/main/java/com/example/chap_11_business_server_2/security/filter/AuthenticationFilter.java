package com.example.chap_11_business_server_2.security.filter;

import com.example.chap_11_business_server_2.security.authentication.JwtAuthentication;
import com.example.chap_11_business_server_2.security.authentication.OtpAuthentication;
import com.example.chap_11_business_server_2.security.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager manager;

    @Value("${jwt.signing.key}")
    private String singingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");
        String jwt = request.getHeader("Authorization");

        if (username != null && password != null) {
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(authentication);
        } else if (code != null) {
            Authentication authentication = new OtpAuthentication(username, code);
            manager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(singingKey.getBytes(StandardCharsets.UTF_8));

            String jwtToken = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwtToken);
        } else if (jwt != null) {
            Authentication authentication = new JwtAuthentication(username, jwt);
            SecurityContextHolder.getContext().setAuthentication(manager.authenticate(authentication));
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
