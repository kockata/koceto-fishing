package kostadin.ecommers.fishing.domain.models.binding;

import kostadin.ecommers.fishing.domain.models.service.CategoryServiceModel;
import kostadin.ecommers.fishing.domain.models.service.OrderServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class ProductAddBindingModel {
    private String brand;
    private String model;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private List<String> categories;
    //private OrderServiceModel order;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

//    public BigDecimal getDiscountedPrice() {
//        return discountedPrice;
//    }
//
//    public void setDiscountedPrice(BigDecimal discountedPrice) {
//        this.discountedPrice = discountedPrice;
//    }
//
//    public OrderServiceModel getOrder() {
//        return order;
//    }
//
//    public void setOrder(OrderServiceModel order) {
//        this.order = order;
//    }
}
