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

        String path = request.getRequestURI();

        // REMOVE O PREFIXO DO CONTEXTO /compras/
        if (path.startsWith("/compras")) {
            path = path.substring("/compras".length());
        }

        String normalizedPath = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;

        boolean isPublic = Arrays.stream(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .anyMatch(ep -> normalizedPath.equals(ep) || normalizedPath.startsWith(ep.replace("/**", "")));

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
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                UsuarioDetailsImpl userDetails = new UsuarioDetailsImpl(usuario);

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }
        return null;
    }
}
