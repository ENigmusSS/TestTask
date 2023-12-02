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
import ua.holovchenko.testtask.entity.ArticleEntity;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.repository.ArticleEntityRepository;
import ua.holovchenko.testtask.service.ArticleService;
import org.junit.jupiter.api.Assertions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
        Mockito.when(this.repo.findAll(PageRequest.of(0,10))).thenReturn(testArticleEntities);

        Assertions.assertEquals(this.service.getArticles(null,null), testArticles);
    }

}
