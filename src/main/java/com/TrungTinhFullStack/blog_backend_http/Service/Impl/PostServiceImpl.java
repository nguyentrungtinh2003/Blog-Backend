package com.TrungTinhFullStack.blog_backend_http.Service.Impl;

import com.TrungTinhFullStack.blog_backend_http.Entity.Category;
import com.TrungTinhFullStack.blog_backend_http.Entity.Notification;
import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Repository.CategoryRepository;
import com.TrungTinhFullStack.blog_backend_http.Repository.NotificationRepository;
import com.TrungTinhFullStack.blog_backend_http.Repository.PostRepository;
import com.TrungTinhFullStack.blog_backend_http.Repository.UserRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.NotificationService;
import com.TrungTinhFullStack.blog_backend_http.Service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;
    private static final String UPLOAD_DIR = "uploads/";

    public Post createPost(String name, String content, Long userId, MultipartFile img, List<String> tags,Long category) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Category category1 = categoryRepository.findById(category).orElse(null);
        // Tạo thư mục uploads nếu chưa tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Xử lý tệp hình ảnh
        String fileName = img.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, img.getBytes());

        // Tạo đối tượng Post
        Post post = new Post();
        post.setName(name);
        post.setContent(content);
        post.setPostedBy(user);
        post.setImg(fileName); // Lưu tên tệp thay vì đường dẫn đầy đủ
        post.setDate(new Date());
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setTags(tags);
        post.setCategory(category1);

//        List<User> user1 = userRepository.findAll();
//        for(User user2 : user1) {
//            Notification notification = new Notification();
//            notification.setMessage("Người dùng "+user.getUsername()+" vừa đăng bài viết "+name);
//            notification.setDate(new Date());
//            notification.setUser(user2);
//            notification.setRead(false);
//            notificationRepository.save(notification);
//        }

       // Lấy danh sách người dùng mà bạn muốn thông báo
        Iterable<User> users = userRepository.findAll(); // Hoặc sử dụng một tiêu chí khác để lấy người dùng

        // Thông báo và lưu thông báo vào DB
        notificationService.notifyUsersAboutNewPost(post.getName(),user.getUsername(), users);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    public void likePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post not found with id : "+postId);
        }
    }

    public void unLikePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post not found with id : "+postId);
        }
    }

    public List<Post> searchByName(String name) {
        return postRepository.findAllByName(name);
    }

    @Override
    public Post updatePost(Long postId, String name, String content, Long userId, MultipartFile img, List<String> tags,Long category) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + postId));
        Category category1 = categoryRepository.findById(category).orElse(null);

        post.setName(name);
        post.setContent(content);
        post.setPostedBy(user);
        post.setTags(tags);
        post.setCategory(category1);


        if (img != null && !img.isEmpty()) {
            byte[] bytes = img.getBytes();
            Path path = Paths.get("uploads/" + img.getOriginalFilename());
            Files.write(path, bytes);
            post.setImg(img.getOriginalFilename());
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
       postRepository.deleteById(postId);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    @Override
    public List<Post> findByPostedById(Long id) {
        return postRepository.findByPostedById(id);
    }

}
