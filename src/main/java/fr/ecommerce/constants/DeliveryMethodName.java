package fr.ecommerce.constants;

public enum DeliveryMethodName {
    STANDARD("Standard"),
    EXPRESS("Express"),
    RELAIS("Point Relais"),
    SAME_DAY("Livraison le jour même");

    private final String displayName;

    DeliveryMethodName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


