package fr.ecommerce.services;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.constants.UserRoleName;
import fr.ecommerce.dto.*;
import fr.ecommerce.mappers.ProducerMapper;
import fr.ecommerce.models.entities.*;
import fr.ecommerce.repositories.ProducerRepository;
import fr.ecommerce.repositories.ProductRepository;
import fr.ecommerce.repositories.RoleRepository;
import fr.ecommerce.repositories.UserRepository;
import fr.ecommerce.responses.ProducerResponseDTO;
import fr.ecommerce.responses.ResponseDTO;
import fr.ecommerce.responses.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Producer service.
 */
@Service
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final UserRepository userRepository;
    private final ProducerMapper producerMapper;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Instantiates a new Producer service.
     *
     * @param producerRepository the producer repository
     * @param userRepository     the user repository
     * @param producerMapper     the producer mapper
     * @param productRepository  the product repository
     * @param passwordEncoder    the password encoder
     * @param roleRepository     the role repository
     */
    public ProducerService(ProducerRepository producerRepository, UserRepository userRepository, ProducerMapper producerMapper, ProductRepository productRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.producerRepository = producerRepository;
        this.userRepository = userRepository;
        this.producerMapper = producerMapper;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    /**
     * Create producer producer response dto.
     *
     * @param dto the dto
     * @return the producer response dto
     */
    public ProducerResponseDTO createProducer(ProducerCreateDTO dto) {

        // Vérifier si l'email existe déjà dans la base
        userRepository.findByEmail(dto.getUser().email())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà !");
                });

        // Convertir le DTO de l'utilisateur en entité User
        User user = producerMapper.toEntity(dto.getUser());

        // Encoder le mot de passe
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Récupérer le rôle "PRODUCER"
        Role producerRole = roleRepository.findByName(UserRoleName.PRODUCER)
                .orElseThrow(() -> new IllegalArgumentException("Le rôle 'PRODUCER' est introuvable dans la base !"));

        // Créer un UserRole
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(producerRole);

        // Ajout de l'association User ↔ Role (via UserRole)
        user.setUserRoles(Set.of(userRole));

        // Sauvegarde de l'utilisateur avec le rôle
        User savedUser = userRepository.save(user);

        // Mapping du DTO ProducerCreateDTO vers l'entité Producer
        Producer producer = producerMapper.toEntity(dto);
        producer.setUser(savedUser);

        // Vérifier et associer les produits si `productIds` est fourni
        if (dto.getProductIds() != null && !dto.getProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(dto.getProductIds());

            // Vérifie que tous les IDs spécifiés existent réellement
            if (products.size() != dto.getProductIds().size()) {
                throw new IllegalArgumentException("Certains produits spécifiés n'ont pas été trouvés !");
            }

            // Synchronise la relation bidirectionnelle Producer ↔ Product
            products.forEach(product -> product.setProducer(producer));

            // Associe les produits au Producer
            producer.setProducts(products);
        } else {
            // Initialise une liste vide si aucun produit n'est associé
            producer.setProducts(new ArrayList<>());
        }

        // Sauvegarder l'entité Producer
        Producer savedProducer = producerRepository.save(producer);

        // Retourner le DTO
        return convertToProducerResponseDTO(savedProducer);
    }


    private ProducerResponseDTO convertToProducerResponseDTO(Producer producer) {
        // Convertir le User en UserResponseDTO
        UserResponseDTO userDTO = new UserResponseDTO(
                producer.getUser().getId(),
                producer.getUser().getFirstname(),
                producer.getUser().getName(),
                producer.getUser().getUsername(),
                producer.getUser().getEmail(),
                producer.getUser().getAccountStatus().name() // Enum AccountStatus
        );

        // Vérifier que la liste de produits n'est pas null, sinon retourner une liste vide
        List<Long> productIds = Optional.ofNullable(producer.getProducts())
                .orElse(List.of()) // Si `products` est null, retourne une liste vide
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        // Retour DTO final
        return new ProducerResponseDTO(
                producer.getId(),
                producer.getDescription(),
                producer.getCompanyName(),
                userDTO,
                productIds // Expose une liste vide si aucun produit associé
        );
    }


    /**
     * Find producer by id producer.
     *
     * @param id the id
     * @return the producer
     */
    public Producer findProducerById(Long id) {
        return producerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producer introuvable avec l'id " + id));
    }

    /**
     * Find all producers page.
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<Producer> findAllProducers(Pageable pageable) {
        return producerRepository.findAll(pageable); // Utilise la méthode native de JpaRepository pour la pagination
    }

    /**
     * Update producer producer.
     *
     * @param id  the id
     * @param dto the dto
     * @return the producer
     */
    public Producer updateProducer(Long id, ProducerCreateDTO dto) {

        Producer existingProducer = producerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producer avec l'id " + id + " introuvable"));

        producerMapper.updateEntityFromDto(dto, existingProducer);

        if (dto.getUser() != null) {
            User existingUser = existingProducer.getUser();
            producerMapper.updateUserFromDto(dto.getUser(), existingUser);
            userRepository.save(existingUser);
        }

        return producerRepository.save(existingProducer);
    }

    /**
     * Gets products by producer id.
     *
     * @param producerId the producer id
     * @return the products by producer id
     */
    public List<ProductResponseDTO> getProductsByProducerId(Long producerId) {
        Producer producer = producerRepository.findByIdWithProducts(producerId)
                .orElseThrow(() -> new RuntimeException("Producer introuvable avec id : " + producerId));

        // Transformer les produits en DTO
        return producer.getProducts().stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                )).collect(Collectors.toList());
    }

    /**
     * Delete producer.
     *
     * @param id the id
     */
    public void deleteProducer(Long id) {
        Producer producer = findProducerById(id);
        producerRepository.delete(producer);
    }
}
