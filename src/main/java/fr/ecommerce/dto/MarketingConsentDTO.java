package fr.ecommerce.dto;

public record MarketingConsentDTO(Long id, Long userId, String consentType, boolean granted)
{}

