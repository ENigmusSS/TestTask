package ua.holovchenko.testtask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.holovchenko.testtask.entity.ArticleEntity;
import ua.holovchenko.testtask.repository.ArticleEntityRepository;

import java.sql.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@WithUserDetails("admin")
public class ArticleIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ArticleEntityRepository repo;

    @Test
    void getArticles_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles");

        this.repo.saveAll(List.of(
                new ArticleEntity("Test1", "Testerov", "Test1", Date.valueOf("2023-02-11")),
                new ArticleEntity("Test2", "Testerenko", "Test2", Date.valueOf("2023-02-11")),
                new ArticleEntity("Test3", "Testerov", "Test3", Date.valueOf("2023-02-10"))));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            [
                                {
                                    "title" : "Test1",
                                    "author" : "Testerov",
                                    "content" : "Test1",
                                    "published" : "2023-02-11"
                                },
                                {
                                    "title" : "Test2",
                                    "author" : "Testerenko",
                                    "content" : "Test2",
                                    "published" : "2023-02-11"
                                },
                                {
                                    "title" : "Test3",
                                    "author" : "Testerov",
                                    "content" : "Test3",
                                    "published" : "2023-02-10"
                                }
                            ]
                        """
                        )
                );
    }
}
