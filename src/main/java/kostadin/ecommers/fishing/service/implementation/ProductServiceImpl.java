package kostadin.ecommers.fishing.service.implementation;

import kostadin.ecommers.fishing.domain.entities.Category;
import kostadin.ecommers.fishing.domain.entities.Product;
import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import kostadin.ecommers.fishing.domain.models.service.ProductServiceModel;
import kostadin.ecommers.fishing.error.ProductAlreadyExistException;
import kostadin.ecommers.fishing.repository.CategoryRepository;
import kostadin.ecommers.fishing.repository.ProductRepository;
import kostadin.ecommers.fishing.service.CategoryService;
import kostadin.ecommers.fishing.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CategoryService categoryService,
            ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel createDefaultProduct(ProductServiceModel model) {
        Product product = this.productRepository
                .findByBrandAndModel(model.getBrand(), model.getModel())
                .orElse(null);

        if (product != null) {
            throw new ProductAlreadyExistException("Product already exist!");
        }
        product = this.modelMapper.map(model, Product.class);
        product = this.productRepository.saveAndFlush(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        Product product = this.productRepository.findById(id).orElse(null);
        return this.modelMapper.map(Objects.requireNonNull(product), ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository
                .findProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        List<CategoryServiceModel> categories = this.categoryService.findAllCategories()
                .stream()
                .filter(c -> productServiceModel.getCategories().contains(c.getId()))
                .collect(Collectors.toList());
        productServiceModel.setCategories(categories);

        product.setCategories(productServiceModel
                .getCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toList())
        );
        product.setModel(productServiceModel.getModel());
        product.setBrand(productServiceModel.getBrand());
        product.setDescription(productServiceModel.getDescription());
        product.setImageUrl(productServiceModel.getImageUrl());
        product.setPrice(productServiceModel.getPrice());

        Product saveProduct = this.productRepository.save(product);
        return this.modelMapper.map(saveProduct, ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = this.productRepository
                .findProductById(id)
                .orElseThrow(IllegalArgumentException::new);
        this.productRepository.delete(product);
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
        return this.productRepository
                .findAll()
                .stream()
                .filter(product -> product
                        .getCategories()
                        .stream()
                        .anyMatch(
                                categoryStream -> categoryStream.getName().equals(category)))
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }
}
