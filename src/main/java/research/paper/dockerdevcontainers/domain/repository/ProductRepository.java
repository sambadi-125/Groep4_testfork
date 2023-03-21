package research.paper.dockerdevcontainers.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import research.paper.dockerdevcontainers.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
