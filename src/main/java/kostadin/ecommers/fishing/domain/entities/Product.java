package kostadin.ecommers.fishing.domain.entities;
import kostadin.ecommers.fishing.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "default_product")
public class Product extends BaseEntity {
    private String brand;
    private String model;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<Category> categories;
    private Order order;

    @Column(name = "brand" , nullable = false)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @Column(name = "model" , nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Column(name = "description" , nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "price" , nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "id"
            )
    )
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
//
//    @ManyToOne(targetEntity = Product.class)
//    @JoinColumn(
//            name = "product_id",
//            referencedColumnName = "id"
//    )
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

  //  @ManyToMany(mappedBy = "products")
  @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order orders) {
        this.order = orders;
    }
}
