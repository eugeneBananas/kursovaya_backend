package ru.mirea.course.util;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mirea.course.dto.UserDTO;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Autowired
    private RestTemplate restTemplate;

    // URL сервиса аутентификации для проверки токена
    private final String AUTH_SERVICE_URL = "http://localhost:8081/auth/validateToken";

    /**
     * Проверяет токен, отправляя запрос в Auth-сервис.
     */
    public boolean validateToken(String token) {
        try {
            logger.info("Validating token with Auth service: {}", token);

            // Создаем тело запроса с токеном
            HttpEntity<String> request = new HttpEntity<>(token);

            // Отправляем POST-запрос с токеном в теле
            ResponseEntity<Void> response = restTemplate.exchange(
                    AUTH_SERVICE_URL, HttpMethod.POST, request, Void.class);

            logger.info("Token validation response: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage(), e);
            return false;
        }
    }


    // Этот метод будет запрашивать данные о пользователе через Auth-сервис.
    private UserDetails loadUserDetailsFromAuthService(String username) {
        try {
            String userInfoUrl = "http://localhost:8081/auth/getUserDetails?username=" + username;
            ResponseEntity<UserDTO> response = restTemplate.exchange(
                    userInfoUrl, HttpMethod.GET, null, UserDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                UserDTO userDetails = response.getBody();
                return User.builder()
                        .username(userDetails.getEmail())
                        .password(userDetails.getPassword())
                        .roles(userDetails.getRole())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching user details: {}", e.getMessage());
            throw new UsernameNotFoundException("User not found", e);
        }
    }



    /**
     * Извлекает имя пользователя (subject) из токена, отправляя запрос в Auth-сервис.
     */
    public String extractUsernameRemote(String token) {
        try {
            logger.info("Extracting username from token: {}", token);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> request = new HttpEntity<>(token, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL + "/extractUsername", HttpMethod.POST, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Failed to extract username: {}", e.getMessage());
            return null;
        }
    }

    public String extractUsername(String token) {
        try {
            logger.info("Extracting username from token.");
            return Jwts.parser()
                    .setSigningKey("")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            throw e;  // Пробрасываем исключение дальше
        }
    }
}
