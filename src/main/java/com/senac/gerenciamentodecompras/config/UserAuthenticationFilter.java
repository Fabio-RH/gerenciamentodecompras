package com.senac.gerenciamentodecompras.config;

import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.UsuarioRepository;
import com.senac.gerenciamentodecompras.service.JwtTokenService;
import com.senac.gerenciamentodecompras.service.UsuarioDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Normalização da URI e logs
        String requestURI = request.getRequestURI();
        String normalizedURI = requestURI.endsWith("/") ? requestURI.substring(0, requestURI.length()-1) : requestURI;

        boolean isPublic = Arrays.stream(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .anyMatch(publicEndpoint -> normalizedURI.equals(publicEndpoint.replace("/**", ""))
                        || normalizedURI.startsWith(publicEndpoint.replace("/**", "")));

        // Logs para debug
        System.out.println("Request URI: " + requestURI);
        System.out.println("Normalized URI: " + normalizedURI);
        System.out.println("É público? " + isPublic);

        // Apenas endpoints protegidos exigem token
        if (!isPublic) {
            String token = recoveryToken(request);

            if (token == null || token.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token ausente");
                return;
            }

            try {
                String subject = jwtTokenService.getSubjectFromToken(token);
                Usuario usuario = usuarioRepository.findByUsuarioEmail(subject)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado no token"));

                UsuarioDetailsImpl userDetails = new UsuarioDetailsImpl(usuario);

                // ❌ Antes: userDetails.getUsername()
                // ✅ Agora: principal é o userDetails completo
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, // principal correto
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        }

        // Continua execução do filtro para endpoints públicos ou após autenticação
        filterChain.doFilter(request, response);
    }

    // Recupera token do header Authorization
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
