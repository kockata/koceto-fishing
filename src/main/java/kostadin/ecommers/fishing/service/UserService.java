package kostadin.ecommers.fishing.service;

import kostadin.ecommers.fishing.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser ( UserServiceModel userServiceModel);
}
