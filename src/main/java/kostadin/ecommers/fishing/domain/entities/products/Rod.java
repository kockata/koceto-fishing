package kostadin.ecommers.fishing.domain.entities.products;

import kostadin.ecommers.fishing.domain.entities.base.Product;

import javax.persistence.*;

@Entity
@Table(name = "rods_product")
public class Rod extends Product {
    private String type;
    private Double size;
    private String power;
//    private kostadin.ecommers.fishing.domain.entities.Product product;

    @Column(name = "type" , nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "size" , nullable = false)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
    @Column(name = "power" )
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

//    @OneToOne(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false, targetEntity = Product.class)
//    public kostadin.ecommers.fishing.domain.entities.Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(kostadin.ecommers.fishing.domain.entities.Product product) {
//        this.product = product;
//    }

}
