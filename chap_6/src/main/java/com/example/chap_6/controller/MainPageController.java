package com.example.chap_6.controller;

import com.example.chap_6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/main")
    public String main(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("products", productService.findAll());
        return "main.html";
    }
}
