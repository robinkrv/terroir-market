package fr.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ProducerUpdateDTO {


    private String description;

    @NotBlank(message = "Le champ companyName ne peut pas être vide.")
    private String companyName;

    private UserUpdateDTO user;

    private List<Long> productIds;

    // Getters et Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UserUpdateDTO getUser() {
        return user;
    }

    public void setUser(UserUpdateDTO user) {
        this.user = user;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}

