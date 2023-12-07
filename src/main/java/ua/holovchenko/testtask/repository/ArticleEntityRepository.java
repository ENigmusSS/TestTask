package ua.holovchenko.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.holovchenko.testtask.entity.ArticleEntity;

import java.sql.Date;
import java.util.UUID;

@Repository
public interface ArticleEntityRepository extends JpaRepository<ArticleEntity, UUID>, JpaSpecificationExecutor<ArticleEntity> {
    long countByPublished(Date published);
}