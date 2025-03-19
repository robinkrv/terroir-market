package fr.ecommerce.models.entities;

import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
