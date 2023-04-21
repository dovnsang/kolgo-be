package com.dtu.kolgo.security;

import com.dtu.kolgo.exception.FilterChainExceptionHandler;
import com.dtu.kolgo.util.constant.Roles;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String ADMIN = Roles.ADMIN.name();
    private final String ENTERPRISE = Roles.ENTERPRISE.name();
    private final String KOL = Roles.KOL.name();
    private final String[] GET_WHITELIST = {
            "/demo",
            "/auth/**",
            "/kols/**",
            "/ents/**",
            "/cities/**",
            "/genders/**",
            "/fields/**",
            "/images/**",
    };
    private final String[] POST_WHITELIST = {
            "/auth/**"
    };
    private final String[] AUTH_WHITELIST = {
            "/users/**",
            "/settings/**",
            "/conversations/**",
            "/websocket/**",
            "/chat/**",
    };
    private final String[] OPEN_API_WHITELIST = {
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Enable CORS
        http.cors();

        // Disable CSRF (Cross Site Request Forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeHttpRequests()
                .requestMatchers("/demo/kol").hasAuthority(KOL)
                .requestMatchers("/demo/ent").hasAnyAuthority(ENTERPRISE)
                .requestMatchers("/demo/admin").hasAuthority(ADMIN)
                .requestMatchers("/websocket/**").permitAll()
                .requestMatchers(OPEN_API_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.GET, GET_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
//                .requestMatchers("/**").hasAuthority(ADMIN)
                .requestMatchers("/**").hasAnyAuthority(ADMIN, KOL, ENTERPRISE)

                // Disallow everything else..
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // Apply JWT
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Apply filter chain exception handler
        http.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class);

        // Handling logout
        http.logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler);

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "content-type", "x-auth-token")
                        .exposedHeaders("x-auth-token");
            }
        };
    }

}
