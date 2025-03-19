package fr.ecommerce.constants;

public enum PaymentMethodName {
    CREDIT_CARD("Carte de Crédit"),
    PAYPAL("PayPal"),
    BANK_TRANSFER("Virement Bancaire");

    private final String displayName;

    PaymentMethodName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
