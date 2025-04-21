package fr.ecommerce.services;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.dto.*;
import fr.ecommerce.mappers.ProducerMapper;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.models.entities.Product;
import fr.ecommerce.models.entities.User;
import fr.ecommerce.repositories.ProducerRepository;
import fr.ecommerce.repositories.ProductRepository;
import fr.ecommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final UserRepository userRepository;
    private final ProducerMapper producerMapper;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public ProducerService(ProducerRepository producerRepository, UserRepository userRepository, ProducerMapper producerMapper, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.producerRepository = producerRepository;
        this.userRepository = userRepository;
        this.producerMapper = producerMapper;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Producer createProducer(ProducerCreateDTO dto) {

        userRepository.findByEmail(dto.getUser().email()).ifPresent(user -> {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà !");
        });

        User user = producerMapper.toEntity(dto.getUser());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        Producer producer = producerMapper.toEntity(dto);
        producer.setUser(savedUser);

        return producerRepository.save(producer);
    }



    public Producer findProducerById(Long id) {
        return producerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producer introuvable avec l'id " + id));
    }

    public Page<Producer> findAllProducers(Pageable pageable) {
        return producerRepository.findAll(pageable); // Utilise la méthode native de JpaRepository pour la pagination
    }

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

    public void deleteProducer(Long id) {
        Producer producer = findProducerById(id);
        producerRepository.delete(producer);
    }
}
