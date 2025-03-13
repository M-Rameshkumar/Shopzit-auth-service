package com.midbenchers.httpconfig;

import com.midbenchers.filter.JwtAuthenticationfilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SpringSecurityconfig {

    private final JwtAuthenticationfilter filter;


    public SpringSecurityconfig(JwtAuthenticationfilter filter) {
        this.filter = filter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth

                                .requestMatchers("/ecom/authentication", "/ecom/reg", "/email/send/**").permitAll()  // Public endpoints
                                .requestMatchers("/ecom/order/**").authenticated()
                                .requestMatchers("/ecom/order/**").hasAuthority("CUSTOMER")
                                .requestMatchers("/ecom/admin/**").hasAuthority("ADMIN")
                                // Authenticated endpoints
                                .anyRequest().denyAll()                                                   // Deny all other requests
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)  // Ensure your filter runs before the default one
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
