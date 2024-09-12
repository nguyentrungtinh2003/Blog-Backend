package com.TrungTinhFullStack.blog_backend_http.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Long statusCode;

    private String message;

    private String token;

    private String refreshToken;

    private String expirationTime;

    private String img;

    private boolean enable;
}
