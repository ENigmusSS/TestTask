package ua.holovchenko.testtask;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.holovchenko.testtask.controller.ArticleController;
import ua.holovchenko.testtask.repository.ArticleEntityRepository;
import ua.holovchenko.testtask.service.ArticleService;

@ExtendWith(MockitoExtension.class)
public class ArticleITTest {
    @Mock
    ArticleEntityRepository repo;
    @InjectMocks
    ArticleService service;

    ArticleController controller;
}
