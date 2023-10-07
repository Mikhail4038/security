package com.keiko.securityapp.configuration;

import com.keiko.securityapp.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder ();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.csrf (csrf -> csrf.disable ());
        http.httpBasic (httpBasic -> httpBasic.disable ());
        http.sessionManagement (session -> session.sessionCreationPolicy (STATELESS));
        http.authorizeHttpRequests (request -> {
            request.requestMatchers ("/auth/**").permitAll ()
                    .requestMatchers ("/**").hasAuthority ("ADMIN")
                    .anyRequest ().authenticated ();
        });
        http.addFilterAfter (jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build ();
    }
}
