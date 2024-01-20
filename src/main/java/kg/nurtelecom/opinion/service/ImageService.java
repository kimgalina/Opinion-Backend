package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String loadImage(MultipartFile image);

    ResponseEntity<String> updateCoverImage(Long articleId, MultipartFile image, User user, String path);
}
