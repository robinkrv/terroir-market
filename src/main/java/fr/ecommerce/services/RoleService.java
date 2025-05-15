package fr.ecommerce.services;

import fr.ecommerce.constants.UserRoleName;
import fr.ecommerce.models.entities.Permission;
import fr.ecommerce.models.entities.Role;
import fr.ecommerce.models.entities.RolePermission;
import fr.ecommerce.repositories.PermissionRepository;
import fr.ecommerce.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    /**
     * Initialisation des rôles et permissions au lancement de l'application.
     */
    @PostConstruct
    public void init() {
        logger.info("=== Initialisation des rôles et permissions ===");

        // Permissions
        Permission readUser = createPermission("READ_USER"); // Lire les utilisateurs
        Permission writeUser = createPermission("WRITE_USER"); // Modifier les utilisateurs
        Permission deleteUser = createPermission("DELETE_USER"); // Supprimer les utilisateurs
        Permission manageRoles = createPermission("MANAGE_ROLES"); // Gérer les rôles
        Permission createProduct = createPermission("MANAGE_PRODUCT"); // Créer des produits

        // Rôles
        Role adminRole = createRole(UserRoleName.ADMIN);
        Role userRole = createRole(UserRoleName.USER);
        Role producerRole = createRole(UserRoleName.PRODUCER);

        // Association des permissions aux rôles
        associatePermissionToRole(adminRole, List.of(readUser, writeUser, deleteUser, manageRoles)); // ADMIN
        associatePermissionToRole(producerRole, List.of(createProduct)); // PRODUCER
        associatePermissionToRole(userRole, List.of(readUser)); // USER

        logger.info("Permissions du rôle ADMIN : {}",
                adminRole.getRolePermissions().stream()
                        .map(r -> r.getPermission().getName())
                        .collect(Collectors.toList()));

        logger.info("Permissions du rôle PRODUCER : {}",
                producerRole.getRolePermissions().stream()
                        .map(r -> r.getPermission().getName())
                        .collect(Collectors.toList()));

        logger.info("Permissions du rôle USER : {}",
                userRole.getRolePermissions().stream()
                        .map(r -> r.getPermission().getName())
                        .collect(Collectors.toList()));

        logger.info("=== Initialisation terminée ===");
    }

    /**
     * Crée une permission si elle n'existe pas.
     *
     * @param name Le nom unique de la permission.
     * @return La permission existante ou nouvellement créée.
     */
    private Permission createPermission(String name) {
        return permissionRepository.findByName(name).orElseGet(() -> {
            Permission permission = new Permission();
            permission.setName(name);
            Permission savedPermission = permissionRepository.save(permission);
            logger.info("Création de la permission : {}", name);
            return savedPermission;
        });
    }

    /**
     * Crée un rôle s'il n'existe pas.
     *
     * @param roleName L'enum définissant le rôle.
     * @return Le rôle existant ou nouvellement créé.
     */
    private Role createRole(UserRoleName roleName) {
        return roleRepository.findByNameWithPermissions(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            Role savedRole = roleRepository.save(role);
            logger.info("Création du rôle : {}", roleName);
            return savedRole;
        });
    }


    /**
     * Associe une liste de permissions à un rôle.
     *
     * @param role        Le rôle auquel associer les permissions.
     * @param permissions La liste de permissions à associer.
     */
    private void associatePermissionToRole(Role role, List<Permission> permissions) {
        permissions.forEach(permission -> {
            boolean alreadyAssociated = role.getRolePermissions() != null &&
                    role.getRolePermissions().stream()
                            .anyMatch(rp -> rp.getPermission().getName().equals(permission.getName()));

            if (!alreadyAssociated) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);

                role.getRolePermissions().add(rolePermission);
                roleRepository.save(role); // Sauvegarde du rôle avec la nouvelle association
                logger.info("Association de la permission '{}' au rôle '{}'", permission.getName(), role.getName());
            }
        });
    }

    /**
     * Recherche un rôle par nom (enum).
     *
     * @param roleName Le rôle à rechercher.
     * @return Un Optional contenant le rôle s'il existe.
     */
    public Optional<Role> findByName(UserRoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}

