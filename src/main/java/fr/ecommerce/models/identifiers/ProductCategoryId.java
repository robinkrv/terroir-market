package fr.ecommerce.models.identifiers;

import java.io.Serializable;
import java.util.Objects;

public class ProductCategoryId implements Serializable {
    private Long productId;
    private Long categoryId;

    public ProductCategoryId() {}

    public ProductCategoryId(Long productId, Long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // ✅ Vérifie si c'est la même instance
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId);
    }

    //@Override
    //public boolean equals(Object o) {
        //if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;
        //ProductCategoryId that = (ProductCategoryId) o;
        //return Objects.equals(productId, that.productId) &&
             //   Objects.equals(categoryId, that.categoryId);
    //}


    //@Override
    //public int hashCode() {
       // return Objects.hash(productId, categoryId);
    //}
}
