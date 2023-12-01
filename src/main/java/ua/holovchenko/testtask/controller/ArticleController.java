package ua.holovchenko.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.service.ArticleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(@Autowired ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok().body(service.getArticles(page, size));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody String title,
                                                 @RequestBody String author,
                                                 @RequestBody String content,
                                                 @RequestBody LocalDate published) {
        if (title.isEmpty()||author.isEmpty()||content.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(service.create(new Article(title, author, content, published)));
    }

    @GetMapping("/stats")
    public ResponseEntity<long[]> getStatistics(@RequestParam int forDays) {
        return ResponseEntity.ok().body(service.getStatistics(forDays));
    }
}
