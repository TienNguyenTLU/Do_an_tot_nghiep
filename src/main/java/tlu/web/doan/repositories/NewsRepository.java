package tlu.web.doan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tlu.web.doan.models.entities.News;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findBySlug(String slug);
}