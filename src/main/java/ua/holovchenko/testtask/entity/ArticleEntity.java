package ua.holovchenko.testtask.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String content;
    @Column
    private Date published;

    public ArticleEntity(String title, String author, String content, Date published) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.published = published;
    }
}
