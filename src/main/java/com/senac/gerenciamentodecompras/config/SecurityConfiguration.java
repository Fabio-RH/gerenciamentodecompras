package com.senac.gerenciamentodecompras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.senac.gerenciamentodecompras.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserAuthenticationFilter userAuthenticationFilter;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                 UserAuthenticationFilter userAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.userAuthenticationFilter = userAuthenticationFilter;
    }

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/usuario/login",
            "/api/usuario/criar",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/home",
            "/download/app",
            "/index.html",
            "/Web.html"
    };

    public static final String[] ENDPOINTS_ADMIN = {
            "/categoria"
    };

    public static final String[] ENDPOINTS_CUSTOMER = {
            "/jogo"
    };

    // ❗ removido "/api/usuario/**" daqui
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/recibo/**",
            "/api/produto/**",
            "/api/lista/**",
            "/api/item/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authenticationProvider()::authenticate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth

                    // ➤ Rotas públicas
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()

                    // ➤ Rotas que exigem login
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()

                    // ➤ Rotas com papéis específicos
                    .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                    .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")

                    // ➤ Todo o resto: permitido
                    .anyRequest().permitAll()
            )
            .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
