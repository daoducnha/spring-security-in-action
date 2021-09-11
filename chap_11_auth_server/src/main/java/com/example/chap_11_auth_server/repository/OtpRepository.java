package com.example.chap_11_auth_server.repository;

import com.example.chap_11_auth_server.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@ResponseBody
public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findOtpByUsername(String username);
}
