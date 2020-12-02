package kostadin.ecommers.fishing.repository;

import kostadin.ecommers.fishing.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByAuthority (String authority);
}
