package com.epharmacy.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam Long userId, @RequestParam String timestamp, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("timestamp", timestamp);
        return "reset-password";
    }
}