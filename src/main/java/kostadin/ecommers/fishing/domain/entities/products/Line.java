//package kostadin.ecommers.fishing.domain.entities.products;
//
//import kostadin.ecommers.fishing.domain.entities.Product;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "lines_product")
//public class Line extends Product {
//
//
//
//    private String material;
//    private Double diameter;
//    private Double limit;
////    private kostadin.ecommers.fishing.domain.entities.Product product;
//
//    @Column(name = "material")
//    public String getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(String material) {
//        this.material = material;
//    }
//
//    @Column(name = "diameter")
//    public Double getDiameter() {
//        return diameter;
//    }
//
//    public void setDiameter(Double diameter) {
//        this.diameter = diameter;
//    }
//
//    @Column(name = "limit_of_line")
//    public Double getLimit() {
//        return limit;
//    }
//
//    public void setLimit(Double limit) {
//        this.limit = limit;
//    }
////    @OneToOne(mappedBy = "lines_product", cascade = CascadeType.ALL,
////            fetch = FetchType.LAZY, optional = false)
////    public kostadin.ecommers.fishing.domain.entities.Product getProduct() {
////        return product;
////    }
////
////    public void setProduct(kostadin.ecommers.fishing.domain.entities.Product product) {
////        this.product = product;
////    }
//
//}
