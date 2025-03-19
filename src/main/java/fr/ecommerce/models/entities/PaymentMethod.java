package fr.ecommerce.models.entities;

import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
