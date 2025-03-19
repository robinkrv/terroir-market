package fr.ecommerce.constants;

public enum AddressType {
    LIVRAISON("Adresse de livraison"),
    FACTURATION("Adresse de facturation");

    private final String displayName;

    AddressType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

