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
}

