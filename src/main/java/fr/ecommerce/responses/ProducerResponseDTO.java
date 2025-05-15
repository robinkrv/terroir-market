package fr.ecommerce.responses;

import java.util.List;

public class ProducerResponseDTO {
    private Long id;
    private String description;
    private String companyName;
    private UserResponseDTO user; // DTO sans le mot de passe associé au producteur
    private List<Long> productIds; // Liste des IDs des produits associés

    public ProducerResponseDTO(Long id, String description, String companyName, UserResponseDTO user, List<Long> productIds) {
        this.id = id;
        this.description = description;
        this.companyName = companyName;
        this.user = user;
        this.productIds = productIds;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
