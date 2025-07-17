package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Provides access to the user data source, enabling operations such as
     * retrieving, saving, updating, and deleting user records. This interface
     * extends JpaRepository and JpaSpecificationExecutor, offering standard
     * CRUD operations and query capabilities.
     *
     * This instance is autowired into the service or controller layer
     * (e.g., {@link CustomUserDetailsService}, {@link UserController}) to facilitate
     * interaction with the user-related database table.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user details for a specific username from the underlying data source.
     * This method is used during the authentication process in Spring Security to
     * retrieve user-specific information such as username, password, and roles.
     *
     * @param username the username of the user to be retrieved.
     * @return an instance of {@link UserDetails} containing the user's information,
     *         including username, password, and granted authorities.
     * @throws UsernameNotFoundException if no user is found with the specified username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}