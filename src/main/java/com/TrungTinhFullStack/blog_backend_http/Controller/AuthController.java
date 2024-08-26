package com.TrungTinhFullStack.blog_backend_http.Controller;

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
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        User user = userService.login(username, password);
        if (user != null) {
            // Return username and user_id in a JSON object
            return ResponseEntity.ok().body(
                    Map.of("username", user.getUsername(), "user_id", user.getId())
            );
        } else {
            // Handle login failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("username") String username,
                                           @RequestParam("password") String password,
                                           @RequestParam("email") String email,
                                           @RequestParam("img") MultipartFile img
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
    public User updateUser(@PathVariable Long id,@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("img") MultipartFile img) {
        return userService.updateUser(id,username,password,email,img);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete User Success");
    }
}
