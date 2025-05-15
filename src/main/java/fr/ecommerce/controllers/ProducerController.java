package fr.ecommerce.controllers;

import fr.ecommerce.dto.ProducerCreateDTO;
import fr.ecommerce.dto.ProductResponseDTO;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.models.entities.Product;
import fr.ecommerce.responses.ProducerResponseDTO;
import fr.ecommerce.services.ProducerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Producer controller.
 */
@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    /**
     * Instantiates a new Producer controller.
     *
     * @param producerService the producer service
     */
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }


    /**
     * Gets producer.
     *
     * @param id the id
     * @return the producer
     */
// 2. Obtenir un Producer par ID
    @GetMapping("/{id}")
    public ResponseEntity<Producer> getProducer(@PathVariable Long id) {
        Producer producer = producerService.findProducerById(id); // Implémenté dans le service
        return ResponseEntity.ok(producer);
    }

    /**
     * Gets producers paged.
     *
     * @param page the page
     * @param size the size
     * @return the producers paged
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<Producer>> getProducersPaged(
            @RequestParam(defaultValue = "0") int page,  // Numéro de la page
            @RequestParam(defaultValue = "10") int size // Taille de la page
    ) {
        Pageable pageable = PageRequest.of(page, size); // Crée une instance de pagination
        Page<Producer> producers = producerService.findAllProducers(pageable);
        return ResponseEntity.ok(producers);
    }

    /**
     * Update producer response entity.
     *
     * @param id  the id
     * @param dto the dto
     * @return the response entity
     */
// 4. Mettre à jour complètement un Producer (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Producer> updateProducer(
            @PathVariable Long id,
            @Valid @RequestBody ProducerCreateDTO dto) {
        Producer updatedProducer = producerService.updateProducer(id, dto);
        return ResponseEntity.ok(updatedProducer);
    }


    /**
     * Gets producer products.
     *
     * @param id the id
     * @return the producer products
     */
    @GetMapping("/{id}/products")
    public List<ProductResponseDTO> getProducerProducts(@PathVariable Long id) {
        return producerService.getProductsByProducerId(id);
    }

    /**
     * Patch producer response entity.
     *
     * @param id  the id
     * @param dto the dto
     * @return the response entity
     */
// 5. Mettre à jour partiellement un Producer (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Producer> patchProducer(
            @PathVariable Long id,
            @RequestBody ProducerCreateDTO dto) {
        Producer patchedProducer = producerService.updateProducer(id, dto); // Gère les mises à jour partielles
        return ResponseEntity.ok(patchedProducer);
    }

    /**
     * Delete producer response entity.
     *
     * @param id the id
     * @return the response entity
     */
// 6. Supprimer un Producer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        producerService.deleteProducer(id); // Implémenté dans le service
        return ResponseEntity.noContent().build();
    }
}

