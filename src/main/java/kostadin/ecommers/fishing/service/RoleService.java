package kostadin.ecommers.fishing.service;

import kostadin.ecommers.fishing.domain.models.service.RoleServiceModel;
import kostadin.ecommers.fishing.domain.models.service.UserServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDb();
    RoleServiceModel findByAuthority (String authority);
   // void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);
    Set<RoleServiceModel> findAllRoles();

}
