package com.senac.gerenciamentodecompras.config;

import com.senac.gerenciamentodecompras.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    // ENDPOINTS PÚBLICOS
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/usuario/login",
            "/api/usuario/criar",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    // ENDPOINTS RESTRITOS (LOGADO)
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/usuario/atualizar/{usuarioId}",
            "/api/usuario/atualizarStatus{usuarioId}",
            "/api/usuario/listar",
            "/api/usuario/listarPorUsuarioId{usuarioId}",
            "/api/usuario/apagar/{usuarioId}",

            "/api/recibo/criar",
            "/api/recibo/atualizar/{reciboId}",
            "/api/recibo/atualizarStatus{reciboId}",
            "/api/recibo/listar",
            "/api/recibo/listarPorReciboId{reciboId}",
            "/api/recibo/apagar/{reciboId}",

            "/api/produto/criar",
            "/api/produto/atualizar/{produtoId}",
            "/api/produto/atualizarStatus{produtoId}",
            "/api/produto/listar",
            "/api/produto/listarPorProdutoId{produtoId}",
            "/api/produto/apagar/{produtoId}",

            "/api/lista/criar",
            "/api/lista/atualizar/{listaId}",
            "/api/lista/atualizarStatus{listaId}",
            "/api/lista/listar",
            "/api/lista/listarPorListaId{listaId}",
            "/api/lista/apagar/{listaId}",

            "/api/item/criar",
            "/api/item/atualizar/{itemId}",
            "/api/item/atualizarStatus{itemId}",
            "/api/item/listar",
            "/api/item/listarPorItemId{itemId}",
            "/api/item/apagar/{itemId}",
    };

    // ENDPOINTS DE CLIENTE
    public static final String[] ENDPOINTS_CUSTOMER = {
            "/jogo"
    };

    // ENDPOINTS DE ADMIN
    public static final String[] ENDPOINTS_ADMIN = {
            "/categoria"
    };

    // ✅ AGORA SIM: PasswordEncoder declarado corretamente
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationProvider configurado com PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // AuthenticationManager usando nosso provider
    @Bean
    public AuthenticationManager authenticationManager() {
        return authenticationProvider()::authenticate;
    }

    // Configuração do filtro de segurança + JWT
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                        .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
