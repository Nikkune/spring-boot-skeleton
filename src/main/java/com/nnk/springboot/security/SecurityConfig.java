package com.nnk.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * A reference to the {@link CustomUserDetailsService} bean, which provides user details
     * for authentication purposes. The service is used to load user-specific data, such as
     * username, password, and roles, from a data source (e.g., database) and integrates
     * with Spring Security for the user authentication process.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Provides a bean for password encoding using the BCrypt hashing algorithm.
     *
     * @return an instance of {@link BCryptPasswordEncoder} for encoding passwords.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager by setting a custom user details service
     * and a password encoder.
     *
     * @param auth the {@link AuthenticationManagerBuilder} to configure the authentication manager
     *             and specify custom authentication details such as user details service
     *             and password encoder.
     * @throws Exception if an error occurs while configuring the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    /**
     * Configures the security filter chain for managing HTTP security in the application.
     *
     * @param http the {@link HttpSecurity} object to configure security settings such as URL patterns, authentication, and authorization.
     * @return the configured {@link SecurityFilterChain} instance.
     * @throws Exception if any error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/bidList/list", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
            );

        return http.build();
    }
}
