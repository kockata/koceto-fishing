package kostadin.ecommers.fishing.repository;

import kostadin.ecommers.fishing.domain.entities.Category;
import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByName(String name);

    Optional<Category> findCategoriesById(String id);


}
