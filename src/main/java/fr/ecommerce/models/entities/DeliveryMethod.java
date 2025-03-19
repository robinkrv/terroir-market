package fr.ecommerce.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.ecommerce.constants.DeliveryMethodName;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name = "delivery_methods")
public class DeliveryMethod extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private DeliveryMethodName name;

    @Column(name = "estimated_delivery_time", nullable = false)
    @JsonFormat(pattern = "EEEE d MMMM", locale = "fr")
    @NotBlank
    private LocalDate estimatedDeliveryTime;

    @Column(name = "extra_fee", nullable = false)
    @DecimalMin("0.0")
    @Positive
    private double extraFee;

    public DeliveryMethodName getName() {
        return name;
    }

    public void setName(DeliveryMethodName name) {
        this.name = name;
    }

    public LocalDate getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDate estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(double extraFee) {
        this.extraFee = extraFee;
    }
}
