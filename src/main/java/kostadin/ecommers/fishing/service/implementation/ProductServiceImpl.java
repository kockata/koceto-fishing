package kostadin.ecommers.fishing.service.implementation;

import kostadin.ecommers.fishing.domain.entities.base.Product;
import kostadin.ecommers.fishing.domain.models.service.ProductServiceModel;
import kostadin.ecommers.fishing.error.ProductAlreadyExistException;
import kostadin.ecommers.fishing.repository.CategoryRepository;
import kostadin.ecommers.fishing.repository.ProductRepository;
import kostadin.ecommers.fishing.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel createDefaultProduct(ProductServiceModel model) {
        Product product = this.productRepository
                .findByBrandAndModel(model.getBrand(), model.getModel())
                .orElse(null);

        if (product!= null) {
            throw new ProductAlreadyExistException("Product already exist!");
        }
        product = this.modelMapper.map(model, Product.class);
        product =this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return null;
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return null;
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        return null;
    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
        return null;
    }
}
