package ua.holovchenko.testtask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.holovchenko.testtask.converter.ArticleConverter;
import ua.holovchenko.testtask.entity.ArticleEntity;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.repository.ArticleEntityRepository;
import ua.holovchenko.testtask.service.ArticleService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    ArticleEntityRepository repo;

    @InjectMocks
    ArticleService service;

    static Page<ArticleEntity> testArticleEntities = new PageImpl<>(List.of(
            new ArticleEntity("Test1", "Testerov", "Test1", Date.valueOf(LocalDate.now())),
            new ArticleEntity("Test2", "Testerenko", "Test2", Date.valueOf(LocalDate.now())),
            new ArticleEntity("Test3", "Testerov", "Test3", Date.valueOf(LocalDate.now().minusDays(1)))));

    static List<Article> testArticles = List.of(
            new Article("Test1", "Testerov", "Test1", LocalDate.now()),
            new Article("Test2", "Testerenko", "Test2", LocalDate.now()),
            new Article("Test3", "Testerov", "Test3", LocalDate.now().minusDays(1)));

    @Test
    void getArticles_noArgs_returnsValidData() {
        Mockito.when(this.repo.findAll(PageRequest.of(0, 10))).thenReturn(testArticleEntities);

        assertEquals(this.service.getArticles(null, null), testArticles);
    }

    @Test
    void getArticles_validArgs_returnsValidData() {
        Mockito.when(this.repo.findAll(PageRequest.of(0, 3))).thenReturn(testArticleEntities);

        assertEquals(this.service.getArticles(1, 3), testArticles);
    }

    @Test
    void getArticles_invalidArgs_returnsValidData() {
        Mockito.when(this.repo.findAll(PageRequest.of(0, 10))).thenReturn(testArticleEntities);

        assertEquals(this.service.getArticles(-7, -34), testArticles);
    }

    @Test
    void createArticle_validArgs_returnsValidData() {
        Article article = testArticles.get(0);
        ArticleEntity entity = ArticleConverter.toEntity(article);
        Mockito.when(this.repo.save(entity)).thenReturn(entity);

        assertEquals(article, this.service.createArticle(article));
    }

    @Test
    void createArticle_invalidArgs_throwsCorrectException() {
        String invalidTitle = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa";

        assertThrows(IllegalArgumentException.class, () -> this.service.createArticle(new Article(invalidTitle, "Testerenko", "comment", LocalDate.now())));
        assertThrows(IllegalArgumentException.class, () -> this.service.createArticle(new Article()));
    }

    @Test
    void getStatistics_returnValidData() {
        long[] ls = {1L, 1L, 1L, 1L, 1L, 1L, 1L};
        for (int i = 0; i < 7; i++) {
            Mockito.when(this.repo.countByPublished(Date.valueOf(LocalDate.now().minusDays(i)))).thenReturn(1L);
            assertEquals(ls[i], this.service.getStatistics(7)[i]);
        }
    }

}
