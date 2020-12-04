package kostadin.ecommers.fishing.service;

import kostadin.ecommers.fishing.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser ( UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    UserServiceModel editUserService (UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUser();

    void setUserRole (String id, String role);
}
