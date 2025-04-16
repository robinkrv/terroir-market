package fr.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ProducerCreateDTO {

    private String description;

    @NotBlank
    private String companyName;


    private RegisterDTO user;

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

    public RegisterDTO getUser() {
        return user;
    }

    public void setUser(RegisterDTO user) {
        this.user = user;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}

