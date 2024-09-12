package com.TrungTinhFullStack.blog_backend_http.observer;

public class PostObserver implements Observer{

    private String userName;

    public PostObserver(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification to " + userName + ": " + message);
    }
}
