package kostadin.ecommers.fishing.web.controllers;
import kostadin.ecommers.fishing.domain.models.binding.UserEditBindingModel;
import kostadin.ecommers.fishing.domain.models.binding.UserRegisterBindingModel;
import kostadin.ecommers.fishing.domain.models.service.UserServiceModel;
import kostadin.ecommers.fishing.domain.models.view.UserAllViewModel;
import kostadin.ecommers.fishing.domain.models.view.UserProfileViewModel;
import kostadin.ecommers.fishing.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("register");
        }
        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return super.redirect("/login");
    }


    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("login");
    }

    @PostMapping("/login")
    public ModelAndView loginRedirect() {
        return redirect("/home");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model",
                        this.modelMapper
                                .map(this.userService
                                                .findUserByUsername(principal.getName()),
                                        UserProfileViewModel.class));
        return super.view("profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model",
                        this.modelMapper
                                .map(this.userService
                                                .findUserByUsername(principal.getName()),
                                        UserProfileViewModel.class));
        return super.view("edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("edit-profile");
        }
        this.userService.editUserService(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());
        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService
                .findAllUser()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities()
                            .stream()
                            .map(a -> a.getAuthority())
                            .collect(Collectors.toSet()));
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return super.view("all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser (@PathVariable String id) {
        this.userService.setUserRole(id, "user");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-superuser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setSuperuser (@PathVariable String id) {
        this.userService.setUserRole(id, "superuser");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin (@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return super.redirect("/users/all");
    }







}
