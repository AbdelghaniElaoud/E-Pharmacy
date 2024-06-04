package com.epharmacy.app.service;

import org.springframework.stereotype.Service;

@Service
public class EmailContentBuilder {

    public String buildPasswordResetEmail(String resetLink) {
        return "<html>" +
                "<body>" +
                "<h1>Password Reset Request</h1>" +
                "<p>We received a request to reset your password. Click the button below to reset it:</p>" +
                "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Reset Password</a>" +
                "<p>If you didn't request a password reset, please ignore this email.</p>" +
                "<p>Thank you,<br>E-Pharmacy</p>" +
                "</body>" +
                "</html>";
    }
}