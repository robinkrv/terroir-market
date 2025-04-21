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

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProducerRepository producerRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
        this.productMapper = productMapper;
    }

    // Créer un produit
    public ProductDTO createProduct(ProductDTO productDTO) {

        if (productDTO.getProducerId() == null) {
            throw new IllegalArgumentException("Le champ producerId est requis.");
        }
        // Trouver le producer lié
        Producer producer = producerRepository.findById(productDTO.getProducerId())
                .orElseThrow(() -> new EntityNotFoundException("Producteur introuvable avec l'id : " + productDTO.getProducerId()));

        // Mapper le DTO vers l'entité
        Product product = productMapper.toEntity(productDTO);
        product.setProducer(producer);

        // Sauvegarder dans la base
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    // Récupérer tous les produits
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer un produit par ID
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + productId));
        return productMapper.toDto(product);
    }

    // Mettre à jour un produit
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + productId));

        // Mapper les champs du DTO sur l'entité
        productMapper.updateEntityFromDto(productDTO, product);

        // Si le producerId est modifié, mettre à jour
        if (productDTO.getProducerId() != null) {
            Producer newProducer = producerRepository.findById(productDTO.getProducerId())
                    .orElseThrow(() -> new EntityNotFoundException("Nouveau producteur introuvable avec l'id : " + productDTO.getProducerId()));
            product.setProducer(newProducer);
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

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


    // Supprimer un produit
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Produit introuvable avec l'id : " + productId);
        }
        productRepository.deleteById(productId);
    }
}

