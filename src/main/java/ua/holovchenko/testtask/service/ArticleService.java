package ua.holovchenko.testtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.holovchenko.testtask.converter.ArticleConverter;
import ua.holovchenko.testtask.entity.ArticleEntity;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.repository.ArticleEntityRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleEntityRepository repo;

    public ArticleService(@Autowired ArticleEntityRepository repo) {
        this.repo = repo;
    }

    public List<Article> getArticles(Integer page, Integer size) {
        if (page == null || page < 1)
            page = 1;

        if (size == null || size < 1)
            size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        return repo.findAll(pageable).stream().map(ArticleConverter::fromEntity).toList();
    }

    public Article createArticle(Article article) throws IllegalArgumentException {
        ArticleEntity entity;
        if (article.getAuthor() == null ||
                article.getTitle() == null ||
                article.getContent() == null ||
                article.getPublished() == null) {
            throw new IllegalArgumentException("All fields is mandatory");
        }
        if (article.getTitle().length() > 100) {
            throw new IllegalArgumentException("Title length must not exceed 100");
        }
        entity = ArticleConverter.toEntity(article);
        return ArticleConverter.fromEntity(repo.save(entity));
    }

    public long[] getStatistics(int forDays) {
        long[] counts = new long[forDays];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = repo.countByPublished(Date.valueOf(LocalDate.now().minusDays(i)));
        }
        return counts;
    }
}
