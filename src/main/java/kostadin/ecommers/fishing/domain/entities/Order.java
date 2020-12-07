package kostadin.ecommers.fishing.domain.entities;

import kostadin.ecommers.fishing.domain.entities.base.BaseEntity;
import kostadin.ecommers.fishing.domain.entities.base.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Set<Product> products;
    private User customer;
    private BigDecimal totalPrice;
    private LocalDateTime finishedOn;

    //    @ManyToMany
//    @JoinTable(
//            name = "orders_products",
//            joinColumns = @JoinColumn(
//                    name = "order_id",
//                    referencedColumnName = "id"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "product_id",
//                    referencedColumnName = "id"
//            )
//    )
    @OneToMany(mappedBy = "order")
    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "finished_on")
    public LocalDateTime getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }
}
