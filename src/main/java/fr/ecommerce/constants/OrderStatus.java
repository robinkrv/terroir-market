package fr.ecommerce.constants;

public enum OrderStatus {
    EN_COURS("En cours"),
    VALIDÉE("Validée"),
    EXPÉDIÉE("Expédiée"),
    LIVRÉE("Livrée");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

