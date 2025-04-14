package fr.ecommerce.dto;

import java.util.List;

public class UserUpdateDTO {
    private String firstname;
    private String name;
    private String email;
    private String phoneNumber;
    private List<AddressDTO> addresses;

    public UserUpdateDTO() {}

    public UserUpdateDTO(String firstname, String name, String email, String phoneNumber,
                         List<AddressDTO> addresses) {
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
    }

    // Getters et setters
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}

