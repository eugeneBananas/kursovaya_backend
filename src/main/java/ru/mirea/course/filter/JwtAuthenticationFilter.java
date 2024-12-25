package ru.mirea.course.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mirea.course.util.JwtUtil;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Логируем URI запроса
        logger.info("Incoming request URI: {}", request.getRequestURI());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            logger.info("Preflight request - skipping authentication filter");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Логируем все заголовки
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header {}: {}", headerName, request.getHeader(headerName));
        }

        // Проверка наличия заголовка Authorization
        String token = request.getHeader("Authorization");

        if (token != null) {
            logger.info("Authorization header: {}", token);
        } else {
            logger.warn("No Authorization header found");
        }

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            logger.info("Extracted token: {}", token);

            try {
                if (!jwtUtil.validateToken(token)) {
                    logger.warn("Token validation failed");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                    return;
                }
            } catch (Exception e) {
                logger.error("Error during token validation", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                return;
            }

            String username = jwtUtil.extractUsernameRemote(token);
            logger.info("Extracted username: {}", username);

            if (username != null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    logger.info("Loaded user details: {}", userDetails);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication successful for user: {}", username);
                } catch (Exception e) {
                    logger.error("Error loading user details for username: {}", username, e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                    return;
                }
            }
        } else {
            logger.warn("Token is missing or does not start with 'Bearer'");
        }

        filterChain.doFilter(request, response);
    }
}
