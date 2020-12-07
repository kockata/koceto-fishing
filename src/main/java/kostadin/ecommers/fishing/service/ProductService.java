package kostadin.ecommers.fishing.service;


import kostadin.ecommers.fishing.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    ProductServiceModel createDefaultProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    void deleteProduct(String id);

    List<ProductServiceModel> findAllByCategory(String category);
}
