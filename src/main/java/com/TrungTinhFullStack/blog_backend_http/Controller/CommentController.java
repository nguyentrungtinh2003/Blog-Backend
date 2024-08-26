package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Entity.Comment;
import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Service.CommentService;
import com.TrungTinhFullStack.blog_backend_http.Service.PostService;
import com.TrungTinhFullStack.blog_backend_http.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @PostMapping("comments")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            Long postId = comment.getPost().getId();
            Long userId = comment.getPostedBy().getId();
            String content = comment.getContent();

            User postedBy = userService.findById(userId); // Lấy thông tin user từ userId
            Post post = postService.findById(postId); // Lấy thông tin post từ postId

            Comment createdComment = commentService.createComment(post, postedBy, content);
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("comments")
    public ResponseEntity<?> getCommentAll() {
        try{
            return ResponseEntity.ok(commentService.getCommentAll());
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }


    @GetMapping("comments/post/{postId}")
    public ResponseEntity<?> getCommentByPostId(@PathVariable Long postId) {
        try{
            return ResponseEntity.ok(commentService.getCommentByPostId(postId));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    @GetMapping("comments/byUser/{userId}")
    public ResponseEntity<?> getCommentByUserId(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(commentService.getCommentByUserId(userId));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    @GetMapping("comments/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        try{
            return ResponseEntity.ok(commentService.getCommentById(commentId));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,@RequestBody Comment comment) {
        try{
            return ResponseEntity.ok(commentService.updateComment(commentId,comment));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try{
            return ResponseEntity.ok(commentService.deleteComment(commentId));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }
}
