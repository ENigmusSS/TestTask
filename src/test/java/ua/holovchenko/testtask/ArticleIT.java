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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@WithUserDetails()
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
        this.repo.deleteAll();
    }

    @Test
    void  createArticle_PayloadValid_ReturnsValidResponseEntity() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                    "title" : "Test1",
                                    "author" : "Testerov",
                                    "content" : "Test1",
                                    "published" : "2023-02-11"
                            }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(
                                """
                            {
                                    "title" : "Test1",
                                    "author" : "Testerov",
                                    "content" : "Test1",
                                    "published" : "2023-02-11"
                            }
                        """
                        )
                );
        assertEquals(new ArticleEntity("Test1", "Testerov", "Test1", Date.valueOf("2023-02-11")), this.repo.findAll().get(0));
    this.repo.deleteAll();
    }

    @Test
    void  createArticle_PayloadNotValid_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                    "title" : "",
                                    "author" : "Testerov",
                                    "content" : "",
                                    "published" : "2023-02-11"
                            }
                        """);
        this.mockMvc.perform(requestBuilder)
                .andExpect(
                        status().isBadRequest()
                );
        assertTrue(this.repo.findAll().isEmpty());
    }

    @Test
    void getStatistics_noArgument_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles/stats");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                    status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            [ 0, 0, 0 ,0 ,0, 0, 0]
                            """)
                );
    }

    @Test
    void getStatistics_withArgument_ReturnsValidResponseEntity() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles/stats").param("forDays", "3");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            [0,0,0]
                            """)
                );
    }
}
