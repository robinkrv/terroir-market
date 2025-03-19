package fr.ecommerce.models.entities;

import fr.ecommerce.constants.AddressType;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull
    private AddressType type;

    @Column(name = "street", nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String street;

    @Column(name = "city", nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String city;

    @Column(name = "postcode", nullable = false)
    @NotBlank
    @Size(min = 4, max = 10)
    private String postcode;


    @Column(name = "country", nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
