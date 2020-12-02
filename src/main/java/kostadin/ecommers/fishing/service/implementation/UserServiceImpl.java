package kostadin.ecommers.fishing.service.implementation;

import kostadin.ecommers.fishing.domain.entities.User;
import kostadin.ecommers.fishing.domain.models.service.UserServiceModel;
import kostadin.ecommers.fishing.repository.UserRepository;
import kostadin.ecommers.fishing.service.RoleService;
import kostadin.ecommers.fishing.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {

        return this.userRepository
                .findByUsername(username)
                .map(u-> this.modelMapper
                                .map(u, UserServiceModel.class))
                .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel editUserService(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository
                .findByUsername(userServiceModel.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("Username not found!"));
        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }
        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());

        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user),UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUser() {
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper
                        .map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }
}
