package com.TrungTinhFullStack.blog_backend_http.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImgService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImg(MultipartFile img) throws IOException {
        Map<?,?> resultImg = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());

        return resultImg.get("url").toString();
    }

    public String updateImg(String oldImg, MultipartFile img) throws IOException {
        String publicId = extractPublicId(oldImg);

        if(publicId != null) {
            cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        }
        Map<?,?> resultImg = cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());

        return resultImg.get("url").toString();
    }

    public String extractPublicId(String oldImg) {
        return ((oldImg== null || oldImg.isEmpty()) ? null: oldImg.substring(oldImg.lastIndexOf("/") + 1, oldImg.lastIndexOf(".")));
    }
}
