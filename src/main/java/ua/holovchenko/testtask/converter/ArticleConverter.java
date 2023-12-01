package ua.holovchenko.testtask.converter;

import ua.holovchenko.testtask.entity.ArticleEntity;
import ua.holovchenko.testtask.model.Article;

import java.sql.Date;

public class ArticleConverter {
    public static Article fromEntity(ArticleEntity entity) {
        return new Article(
                entity.getTitle(),
                entity.getAuthor(),
                entity.getContent(),
                entity.getPublished().toLocalDate());
    }
    public static ArticleEntity toEntity(Article article) {
        return new ArticleEntity(
                article.getTitle(),
                article.getAuthor(),
                article.getContent(),
                Date.valueOf(article.getPublished()));
    }
}
