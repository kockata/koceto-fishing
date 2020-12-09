package kostadin.ecommers.fishing.repository;

import kostadin.ecommers.fishing.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByBrand(String brand);

    Optional<Product> findByBrandAndModel (String brand, String model);

    Optional<Product> findProductById(String id);
}
