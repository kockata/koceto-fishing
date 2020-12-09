package kostadin.ecommers.fishing.domain.entities;

import kostadin.ecommers.fishing.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;

    @Column(name = "category_name",nullable = false, unique = true, updatable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
