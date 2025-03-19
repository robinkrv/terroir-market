package fr.ecommerce.models.entities;

import fr.ecommerce.constants.OrderStatus;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_number", nullable = false, unique = true)
    @NotBlank
    @Size(min = 6, max = 20)
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "L'order number doit contenir uniquement des lettres majuscules, chiffres et tirets.")
    private String orderNumber;

    @Column(name = "tracking_number", unique = true)
    @NotBlank
    @Size(min = 10, max = 30) // Longueur du numéro de suivi
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Le tracking number doit contenir uniquement des lettres majuscules et chiffres.")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotBlank
    private OrderStatus status;

    @Column(name = "total_ht", nullable = false)
    @DecimalMin("0.0")
    @NotBlank
    private BigDecimal totalHT;

    @Column(name = "total_ttc", nullable = false)
    @DecimalMin("0.0")
    @NotBlank
    private BigDecimal totalTTC;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}