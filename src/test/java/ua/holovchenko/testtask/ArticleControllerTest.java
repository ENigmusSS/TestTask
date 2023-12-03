package ua.holovchenko.testtask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.holovchenko.testtask.controller.ArticleController;
import ua.holovchenko.testtask.model.Article;
import ua.holovchenko.testtask.service.ArticleService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {
    @Mock
    ArticleService articleService;

    @InjectMocks
    ArticleController controller;

    static List<Article> testArticles = List.of(
            new Article("Test1", "Testerov", "Test1", LocalDate.now()),
            new Article("Test2", "Testerenko", "Test2", LocalDate.now()),
            new Article("Test3", "Testerov", "Test3", LocalDate.now().minusDays(1)));
    @Test
    void getArticles_ReturnsValidResponseEntity() {
        List<Article> articles = testArticles;
        Mockito.doReturn(articles).when(this.articleService).getArticles(1,10);

        ResponseEntity<List<Article>> responseEntity = this.controller.getArticles(1,10);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(articles, responseEntity.getBody());
    }

    @Test
    void  createArticle_PayloadValid_ReturnsValidResponseEntity() {
        Article test = new Article("Test1", "Testerov", "Test1", LocalDate.now());
        Mockito.doReturn(test).when(this.articleService).createArticle(test);

        ResponseEntity<Article> responseEntity = this.controller.createArticle(test.getTitle(), test.getAuthor(), test.getContent(), test.getPublished().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(test, responseEntity.getBody());
    }

    @Test
    void createArticle_PayloadNotValid_ReturnsValidResponseEntity() {
        ResponseEntity<Article> responseEntity = this.controller.createArticle("Test2", "", "Test2", LocalDate.now().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void getStatistics_noArgument_ReturnsValidResponseEntity() {
        long[] stats = {0L,0L,0L,0L,0L,0L,0L};
        Mockito.when(this.articleService.getStatistics(7)).thenReturn(stats);

        ResponseEntity<long[]> responseEntity = this.controller.getStatistics(null);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(stats, responseEntity.getBody());
    }

    @Test
    void getStatistics_withArgument_ReturnsValidResponseEntity() {
        long[] stats = {0L,0L,0L};
        Mockito.when(this.articleService.getStatistics(3)).thenReturn(stats);

        ResponseEntity<long[]> responseEntity = this.controller.getStatistics(3);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(stats, responseEntity.getBody());
    }
}
