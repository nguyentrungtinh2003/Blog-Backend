package com.TrungTinhFullStack.blog_backend_http.Service;

import com.TrungTinhFullStack.blog_backend_http.Entity.Comment;
import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;

import java.util.List;

public interface CommentService {

    Comment createComment(Post post, User postedBy, String content);
    List<Comment> getCommentByPostId(Long postId);
    List<Comment> getCommentAll();
    List<Comment> getCommentByUserId(Long userId);
    Comment getCommentById(Long commentId);
    Comment updateComment(Long commentId, Comment comment);
    Comment deleteComment(Long commentId);
}
