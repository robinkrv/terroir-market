package fr.ecommerce.services;

import fr.ecommerce.models.entities.User;
import fr.ecommerce.models.entities.UserRole;
import fr.ecommerce.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type User details service.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param userRepository the user repository
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Rechercher l'utilisateur avec ses rôles chargés via une requête "JOIN FETCH"
        User user = userRepository.findByUsernameOrEmailWithRoles(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur non trouvé avec cet identifiant (username ou email) : " + usernameOrEmail));

        // Construire les autorités (rôles et permissions)
        List<GrantedAuthority> authorities = user.getUserRoles().stream()
                .map(UserRole::getRole) // Mapper tous les rôles liés à l'utilisateur
                .flatMap(role -> {
                    // Ajouter les permissions associées au rôle
                    Stream<GrantedAuthority> permissions = role.getRolePermissions().stream()
                            .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().getName()));

                    // Ajouter également le rôle (préfixé avec "ROLE_")
                    Stream<GrantedAuthority> roleAuthorities = Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));

                    // Combiner les permissions et le rôle
                    return Stream.concat(permissions, roleAuthorities);
                })
                .collect(Collectors.toList());

        System.out.println("Utilisateur '" + usernameOrEmail + "' avec les rôles et permissions : " +
                authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));


        // Construire et retourner l'objet UserDetails pour Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),       // Email comme identifiant principal
                user.getPassword(),    // Mot de passe encodé
                authorities            // Liste des rôles et permissions
        );
    }



}


