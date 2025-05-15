package fr.ecommerce.controllers;

import fr.ecommerce.dto.ProductDTO;
import fr.ecommerce.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Instantiates a new Product controller.
     *
     * @param productService the product service
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create product response entity.
     *
     * @param productDTO the product dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    /**
     * Gets all products.
     *
     * @return the all products
     */
// Récupérer tous les produits
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
// Récupérer un produit par son ID
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Update product product dto.
     *
     * @param id         the id
     * @param productDTO the product dto
     * @return the product dto
     */
// Mettre à jour un produit
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    /**
     * Associate products to producer response entity.
     *
     * @param producerId the producer id
     * @param productIds the product ids
     * @return the response entity
     */
    @PutMapping("/associate/{producerId}")
    public ResponseEntity<Void> associateProductsToProducer(
            @PathVariable Long producerId,
            @RequestBody List<Long> productIds) {
        productService.associateProductsToProducer(producerId, productIds);
        return ResponseEntity.ok().build();
    }


    /**
     * Delete product.
     *
     * @param id the id
     */
// Supprimer un produit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

