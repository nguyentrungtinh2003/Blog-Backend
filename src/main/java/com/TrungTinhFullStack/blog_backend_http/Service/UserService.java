package com.TrungTinhFullStack.blog_backend_http.Service;

import com.TrungTinhFullStack.blog_backend_http.Dto.ReqRes;
import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Repository.PostRepository;
import com.TrungTinhFullStack.blog_backend_http.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.MemberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Configuration
public interface UserService {

    ReqRes login(ReqRes reqRes, HttpServletResponse response);
    User register(String username,String password,String email,MultipartFile img) throws IOException;
    void createInitialAdmin() throws IOException;
    String hashPassword(String password);
    User findById(Long userId);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, String username, String password, String email, MultipartFile img);
    void deleteUser(Long id);
    void enableUser(Long userId, boolean enable);
    String sendOtpToEmail(String email);
    String verifyOtpAndChangePassword(String email, String otp, String newPassword);
}
