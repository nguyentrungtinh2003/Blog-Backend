package com.TrungTinhFullStack.blog_backend_http.Service.Impl;

import com.TrungTinhFullStack.blog_backend_http.Dto.ReqRes;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Repository.NotificationRepository;
import com.TrungTinhFullStack.blog_backend_http.Repository.UserRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.EmailService;
import com.TrungTinhFullStack.blog_backend_http.Service.Jwt.JwtUtils;
import com.TrungTinhFullStack.blog_backend_http.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    private static final String UPLOAD_DIR = "/uploads";


    @Override
    public ReqRes login(ReqRes reqRes, HttpServletResponse response) {
        ReqRes res = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqRes.getUsername(), reqRes.getPassword()));

            var user = userRepository.findByUsername(reqRes.getUsername());
            if (user == null) {
                throw new BadCredentialsException("User not found");
            }

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            // Tạo cookie chứa JWT token
            Cookie jwtCookie = new Cookie("authToken", jwt);
            jwtCookie.setHttpOnly(true); // Cookie không thể truy cập từ JavaScript để bảo mật
            jwtCookie.setMaxAge(24 * 60 * 60); // Cookie hết hạn sau 24 giờ
            jwtCookie.setPath("/"); // Có hiệu lực trên toàn bộ ứng dụng
            response.addCookie(jwtCookie); // Thêm cookie vào phản hồi

            res.setStatusCode(200L);
            res.setMessage("User login success !");
            res.setId(user.getId());
            res.setUsername(reqRes.getUsername());
            res.setImg(user.getImg());
            res.setToken(jwt);
            res.setRefreshToken(refreshToken);
            res.setExpirationTime("24Hrs");
            res.setEnable(user.isEnabled());

            return res;

        } catch (BadCredentialsException e) {
            res.setStatusCode(403L);
            res.setMessage("Invalid credentials");
            return res;
        } catch (Exception e) {
            res.setStatusCode(500L);
            res.setMessage(e.getMessage());
            return res;
        }
    }

    @Override
    public User register(String username,String password,String email,MultipartFile img) throws IOException {
        // Check if username already exists
        User user = new User();
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        // Tạo thư mục uploads nếu chưa tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Xử lý tệp hình ảnh
        String fileName = img.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, img.getBytes());


        // Hash the password before saving
//        String hashedPassword = hashPassword(password);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        user.setEmail(email);
        user.setImg(fileName);
        user.setEnabled(true);

        // Save the user
        return userRepository.save(user);
    }

    @Override
    public void createInitialAdmin() throws IOException {
        // Check if admin already exists
        if (userRepository.findByUsername("admin") != null) {
            return; // Admin already exists
        }

        // Create a new admin user
        User admin = User.builder()
                .username("admin")
                .password(hashPassword("admin270903"))
                .email("admin@gmail.com")
                .img("admin.png")// Default password "admin"
                .build();
        // Set other properties if needed

        userRepository.save(admin);
    }

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Update User Logic..
    @Override
    public User updateUser(Long id, String username, String password, String email, MultipartFile img) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(username);
                    if (password != null && !password.isEmpty()) {
                        user.setPassword(passwordEncoder.encode(password));
                    }
                    user.setEmail(email);
                    // Update other fields as necessary
                    if (img != null && !img.isEmpty()) {
                        user.setImg(img.getOriginalFilename());
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Delete userlogic.....
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        notificationRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    @Override
    public void enableUser(Long userId, boolean enable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enable);
        userRepository.save(user);
    }

    @Override
    public String sendOtpToEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return("Email không tồn tại trong hệ thống");
        }

        // Tạo OTP ngẫu nhiên
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu OTP và thời gian hết hạn
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        // Gửi email OTP
        emailService.send(user.getEmail(), "Mã OTP của bạn",
                "Mã OTP của bạn là: " + otp + ". OTP này có hiệu lực trong 10 phút.");

        return "Mã OTP đã được gửi đến email của bạn.";
    }

    @Override
    public String verifyOtpAndChangePassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return("Email không tồn tại");
        }

        // Kiểm tra OTP và thời gian hết hạn
        if (!user.getOtp().equals(otp)) {
            throw new IllegalArgumentException("OTP không chính xác");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP đã hết hạn");
        }

        // Cập nhật mật khẩu mới (mã hóa mật khẩu)
        user.setPassword(passwordEncoder.encode(newPassword));

        // Xóa OTP sau khi dùng
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        return "Mật khẩu đã thay đổi thành công!";
    }


}
