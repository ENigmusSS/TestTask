package ua.holovchenko.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(@Autowired ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok().body(service.getArticles(page, size));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
            if (article.getTitle().isEmpty()||
                    article.getAuthor().isEmpty()||
                    article.getContent().isEmpty()||
                    article.getPublished()==null) {
                return ResponseEntity.badRequest().build();
            }
        return ResponseEntity.ok().body(service.createArticle(article));
    }

    @GetMapping("/stats")
    public ResponseEntity<long[]> getStatistics(@RequestParam(required = false) Integer forDays) {
        if (forDays == null || forDays == 0) {
            forDays = 7;
        }
        return ResponseEntity.ok().body(service.getStatistics(forDays));
    }
}
