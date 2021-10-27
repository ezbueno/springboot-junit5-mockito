package com.buenoezandro.api.config;

import com.buenoezandro.api.domain.User;
import com.buenoezandro.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@Profile(value = "test")
public class TestConfig {

    private final UserRepository userRepository;

    @Bean
    public void startDB() {
        var user = new User(null, "Ezandro", "ezandro@teste.com", "123");
        var user2 = new User(null, "Bueno", "bueno@teste.com", "321");
        this.userRepository.saveAll(List.of(user, user2));
    }
}
