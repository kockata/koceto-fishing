package kostadin.ecommers.fishing.web.controllers;

import kostadin.ecommers.fishing.domain.models.binding.ProductAddBindingModel;
import kostadin.ecommers.fishing.domain.models.service.ProductServiceModel;
import kostadin.ecommers.fishing.domain.models.view.ProductAllViewModel;
import kostadin.ecommers.fishing.domain.models.view.ProductDetailsViewModel;
import kostadin.ecommers.fishing.service.CategoryService;
import kostadin.ecommers.fishing.service.CloudinaryService;
import kostadin.ecommers.fishing.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addProduct() {
        return view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.createDefaultProduct(productServiceModel);
        return redirect("/products/all");
    }

    @GetMapping("/all")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList()));
        return view("/product/all-products", modelAndView);
    }

    @GetMapping("details/{id}")
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