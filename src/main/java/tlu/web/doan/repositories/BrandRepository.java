package tlu.web.doan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tlu.web.doan.models.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
