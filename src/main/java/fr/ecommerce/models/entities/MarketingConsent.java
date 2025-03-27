package fr.ecommerce.models.entities;

import fr.ecommerce.constants.ConsentType;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "marketing_consents")
public class MarketingConsent extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "consent_type", nullable = false)
    private ConsentType consentType;

    @Column(name = "granted", nullable = false)
    private boolean granted;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConsentType getConsentType() {
        return consentType;
    }

    public void setConsentType(ConsentType consentType) {
        this.consentType = consentType;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
}

