package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendWarning")
    public String sendEmail(@RequestBody Map<String, String> request) {
        String checkEmail = request.get("email");

        if (checkEmail == null || checkEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address is missing or invalid");
        }

        // Gửi email
        emailService.sendEmail(checkEmail, "Thông báo vi phạm",
                "Bạn có hành vi không đúng qui tắc trên trang Blog của chúng tôi, vui lòng không sai phạm, nếu sai phạm thêm, chúng tôi sẽ xoá tài khoản của bạn ngay !");

        return "Email đã được gửi cho User " + checkEmail;
    }
}
