package com.example.multilayercachedemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).hasRole("ACTUATOR")
                        .anyRequest()
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(actuatorAuthenticationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public ActuatorAuthenticationFilter actuatorAuthenticationFilter() {
        return new ActuatorAuthenticationFilter(securityProperties);
    }
}
