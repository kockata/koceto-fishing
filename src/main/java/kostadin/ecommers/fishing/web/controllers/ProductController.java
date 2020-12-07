package kostadin.ecommers.fishing.web.controllers;

import kostadin.ecommers.fishing.domain.models.binding.ProductAddBindingModel;
import kostadin.ecommers.fishing.domain.models.service.ProductServiceModel;
import kostadin.ecommers.fishing.domain.models.view.ProductAllViewModel;
import kostadin.ecommers.fishing.domain.models.view.ProductDetailsViewModel;
import kostadin.ecommers.fishing.service.CategoryService;
import kostadin.ecommers.fishing.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController extends BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct(@ModelAttribute ProductAddBindingModel model) {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );
        productServiceModel.setImageUrl(model.getImageUrl());
        this.productService.createDefaultProduct(productServiceModel);
        return redirect("products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAnonymous()")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList()));
        return view("products/all-products");
    }

    @GetMapping("details/{id}")
    @PreAuthorize("isAnonymous()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productById = this.productService.findProductById(id);
        ProductDetailsViewModel model = this.modelMapper.map(productById, ProductDetailsViewModel.class);
        modelAndView.addObject("product", model);
        return view("product/details-product", modelAndView);
    }

//    @PostMapping("details/{id}")
//    @PreAuthorize("hasRole('ROLE_MODERATOR')")
//    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
//
//    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductAllViewModel> fetchByCategory(@PathVariable String category) {
        if(category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }
}