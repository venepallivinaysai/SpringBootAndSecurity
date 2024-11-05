package com.springBlog.SpringBlog.configuration;

import com.springBlog.SpringBlog.security.JWTAuthenticationEntryPoint;
import com.springBlog.SpringBlog.security.JWTAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "securityName",
        description = "adding security for swagger",
        bearerFormat = "JWT",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP
)
public class SecurityConfiguration {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/posts/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**","/v3/**").permitAll()
                                .anyRequest()
                                .authenticated()
                ).exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // before spring security going to UsernamePasswordAuthenticationFilter for generating
        // UsernamePasswordAuthenticationToken . we have written our code for validating through JWT TOKEN.
        // By passing it to jwtAuthenticationFilter.
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // InMemory USER DETAILS VALIDATION
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails vinay = User.builder()
//                .username("vinay")
//                .password(passwordEncoder().encode("vinay"))
//                .roles("ADMIN")
//                .build();
//        UserDetails uday = User.builder()
//                .username("uday")
//                .password(passwordEncoder().encode("uday"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(vinay, uday);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return  configuration.getAuthenticationManager();
    }
}
