//package ru.mirea.course.filter;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtValidationFilter extends OncePerRequestFilter {
//
//    @Value("${auth-service.url}") // Этот URL можно хранить в конфигурации
//    private String authUrl;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);  // Убираем "Bearer " и оставляем сам токен
//
//            // Проверяем токен в auth-сервисе
//            if (!isTokenValid(token)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid Token");
//                return; // Если токен невалиден, не продолжаем обработку
//            }
//        }
//
//        filterChain.doFilter(request, response); // Если токен валиден, продолжаем обработку запроса
//    }
//
//    private boolean isTokenValid(String token) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(authUrl + "/api/validate", HttpMethod.GET, entity, String.class);
//            return response.getStatusCode() == HttpStatus.OK; // Если токен валиден
//        } catch (Exception e) {
//            return false;  // Если произошла ошибка при запросе к сервису auth, считаем токен невалидным
//        }
//    }
//}
