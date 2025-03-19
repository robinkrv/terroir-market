package fr.ecommerce.models.entities;

import fr.ecommerce.models.base.AbstractTimestampedEntity;
import fr.ecommerce.models.identifiers.ProductCategoryId;
import jakarta.persistence.*;

@Entity
@Table(name = "product_categories")
public class ProductCategory extends AbstractTimestampedEntity {

    @EmbeddedId
    private ProductCategoryId id;  // Clé composite

    @ManyToOne
    @MapsId("productId")  // Relie la clé composite au produit
    private Product product;  // Produit dans la liaison

    @ManyToOne
    @MapsId("categoryId")  // Relie la clé composite à la catégorie
    private Category category;  // Catégorie dans la liaison

    // Constructeur vide pour JPA
    public ProductCategory() {}

    // Constructeur personnalisé pour initialiser l'entité
    public ProductCategory(Product product, Category category) {
        this.id = new ProductCategoryId(product.getId(), category.getId());
        this.product = product;
        this.category = category;
    }

    // Getters et setters
    public ProductCategoryId getId() {
        return id;
    }

    public void setId(ProductCategoryId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
