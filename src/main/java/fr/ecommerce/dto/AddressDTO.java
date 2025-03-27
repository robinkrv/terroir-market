package fr.ecommerce.dto;

public record AddressDTO(Long id, String type, String street, String city, String postcode, String country, Long userId)
{}

