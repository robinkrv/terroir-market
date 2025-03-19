package fr.ecommerce.models.entities;

import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
public class Promotion extends AbstractEntity {
    @Column(name = "promo_code", nullable = false, unique = true)
    @NotBlank
    @Size(min = 1,max = 5)
    private String promoCode;

    @Column(name = "percentage", nullable = false)
    @Min(1)
    @Max(100)
    private int percentage;

    @Column(name = "discount_amount", nullable = false)
    @DecimalMin("0.0")
    private double discountAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "promotion")
    private List<ProductPromotion> productPromotions;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<ProductPromotion> getProductPromotions() {
        return productPromotions;
    }

    public void setProductPromotions(List<ProductPromotion> productPromotions) {
        this.productPromotions = productPromotions;
    }
}
