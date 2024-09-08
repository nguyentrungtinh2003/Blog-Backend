package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Dto.ReqRes;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ReqRes reqRes) {
        return ResponseEntity.ok(userService.login(reqRes));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestPart("username") String username,
                                           @RequestPart("password") String password,
                                           @RequestPart("email") String email,
                                           @RequestPart("img") MultipartFile img
                                          ) {
        try {
            userService.register(username,password,email,img);
            return ResponseEntity.ok("Registration successful!");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
           return  userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id,@RequestPart("username") String username,
                           @RequestPart("password") String password,
                           @RequestPart("email") String email,
                           @RequestPart(value = "img", required = false) MultipartFile img
                           ) {
        return userService.updateUser(id,username,password,email,img);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete User Success");
    }
}
