package kostadin.ecommers.fishing.web.controllers;

import kostadin.ecommers.fishing.domain.models.binding.CategoryAddBindingModel;
import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import kostadin.ecommers.fishing.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addCategory() {
        return view("category/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryAddBindingModel model) {
        this.categoryService.addCategory(this.modelMapper.map(model, CategoryServiceModel.class));
        return redirect("/categories/all");
    }

//    @GetMapping("/all")
//    @PreAuthorize("isAnonymous()")
//    public ModelAndView getCategories() {
//
//    }

}
