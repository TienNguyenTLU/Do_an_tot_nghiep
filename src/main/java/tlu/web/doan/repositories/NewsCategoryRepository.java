package tlu.web.doan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tlu.web.doan.models.entities.NewsCategory;
import java.util.Optional;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
    Optional<NewsCategory> findBySlug(String slug);
}