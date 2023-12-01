package ua.holovchenko.testtask.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Article {
    private String title;
    private String author;
    private String content;
    private LocalDate published;
}
