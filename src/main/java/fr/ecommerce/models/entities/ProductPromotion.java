package fr.ecommerce.models.entities;

import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_promotions")
public class ProductPromotion extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}

