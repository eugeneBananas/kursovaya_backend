package ru.mirea.course.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mirea.course.dto.UserDTO;

@Service
public class MyUserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    public MyUserDetailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to load user by username: {}", username);

        // URL для запроса к Auth-сервису
        String authServiceUrl = "http://localhost:8081/auth/getUserDetails?username=" + username;

        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(authServiceUrl, HttpMethod.GET, null, UserDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                UserDTO userDTO = response.getBody();
                logger.info("Found user: {}", userDTO.getEmail());

                return User.builder()
                        .username(userDTO.getEmail())
                        .password(userDTO.getPassword()) // Убедитесь, что пароль зашифрован
                        .roles(userDTO.getRole())  // Роли
                        .build();
            } else {
                logger.error("User not found with email: {}", username);
                throw new UsernameNotFoundException("User not found");
            }
        } catch (Exception e) {
            logger.error("Error while fetching user details from Auth service: {}", e.getMessage());
            throw new UsernameNotFoundException("User not found", e);
        }
    }
}
