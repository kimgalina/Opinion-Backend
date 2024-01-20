package kg.nurtelecom.opinion.service;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.payload.article.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    ResponseEntity<ArticleResponse> createArticle(ArticleRequest article, User user);

    ResponseEntity<ArticleResponse> editArticle(ArticleRequest editedArticle, Long id);

    ResponseEntity<ArticleGetDTO> getArticle(Long id, User user);

    ResponseEntity<Void> deleteArticle(Long id);

    ResponseEntity<Page<ArticlesGetDTO>> getMyArticles(User user, Pageable pageable);

    ResponseEntity<Page<ArticlesGetDTO>> getArticles(Pageable pageable, User user);

}
