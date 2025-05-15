package fr.ecommerce.services;

import fr.ecommerce.dto.ProductDTO;
import fr.ecommerce.mappers.ProductMapper;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.models.entities.Product;
import fr.ecommerce.repositories.ProducerRepository;
import fr.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Product service.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final ProductMapper productMapper;
    private final UserService userService;

    /**
     * Instantiates a new Product service.
     *
     * @param productRepository  the product repository
     * @param producerRepository the producer repository
     * @param productMapper      the product mapper
     * @param userService        the user service
     */
    public ProductService(ProductRepository productRepository, ProducerRepository producerRepository, ProductMapper productMapper, UserService userService) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
        this.productMapper = productMapper;
        this.userService = userService;
    }

    /**
     * Create product product dto.
     *
     * @param productDTO the product dto
     * @return the product dto
     */
    // Créer un produit
    public ProductDTO createProduct(ProductDTO productDTO) {

        Long currentProducerId = userService.getCurrentUserId();

        // Trouver le producer lié
        Producer producer = producerRepository.findById(currentProducerId)
                .orElseThrow(() -> new EntityNotFoundException("Producteur introuvable avec l'id : " + currentProducerId));

        // Mapper le DTO vers l'entité
        Product product = productMapper.toEntity(productDTO);
        product.setProducer(producer);

        // Sauvegarder dans la base
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
// Récupérer tous les produits
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets product by id.
     *
     * @param productId the product id
     * @return the product by id
     */
// Récupérer un produit par ID
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + productId));
        return productMapper.toDto(product);
    }

    /**
     * Update product product dto.
     *
     * @param productId  the product id
     * @param productDTO the product dto
     * @return the product dto
     */
// Mettre à jour un produit
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Long currentProducerId = userService.getCurrentUserId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + productId));

        if (!product.getProducer().getId().equals(currentProducerId)) {
            throw new SecurityException("Vous n'avez pas l'autorisation de modifier ce produit.");
        }

        // Mapper les champs du DTO sur l'entité
        productMapper.updateEntityFromDto(productDTO, product);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Associate products to producer.
     *
     * @param producerId the producer id
     * @param productIds the product ids
     */
    public void associateProductsToProducer(Long producerId, List<Long> productIds) {
        // Vérifier l'existence du producteur
        Producer producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new EntityNotFoundException("Producteur introuvable avec l'id : " + producerId));

        // Rechercher les produits à associer
        List<Product> products = productRepository.findAllById(productIds);

        // Effectuer l'association
        for (Product product : products) {
            product.setProducer(producer);
        }

        // Sauvegarder les produits mis à jour
        productRepository.saveAll(products);
    }


    /**
     * Delete product.
     *
     * @param productId the product id
     */
// Supprimer un produit
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Produit introuvable avec l'id : " + productId);
        }
        productRepository.deleteById(productId);
    }
}

