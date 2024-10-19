package com.example.platenwinkel.config;

import com.example.platenwinkel.filter.JwtRequestFilter;
import com.example.platenwinkel.service.MyUserDetailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final MyUserDetailService myUserDetailService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(MyUserDetailService myUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.myUserDetailService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(myUserDetailService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth

                                // Public endpoints
                                .requestMatchers("/authenticate").permitAll()
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/lpproducts").permitAll()
                                .requestMatchers(HttpMethod.GET, "/lpproducts/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()

                                .requestMatchers(HttpMethod.GET, "/authenticated").authenticated()
                                .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/invoices").hasAnyRole("ADMIN", "USER") // USERS can get all invoices
                                .requestMatchers(HttpMethod.GET, "/invoices/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/orders").hasRole("USER")


                                .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/lpproducts").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/lpproducts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/lpproducts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/orders/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/invoices").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/invoices/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/invoices/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/reports/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/reports/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/reports").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/reports/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/reports/**").hasRole("ADMIN")

                                // Default to deny all other requests
                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


//dit werkt als je uit eindelijk niet uitkomt hier boven
//package com.example.platenwinkel.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//    @Configuration
//    @EnableWebSecurity
//    public class SpringSecurityConfig {
//
//        @Bean
//        protected SecurityFilterChain filter (HttpSecurity http) throws Exception {
//
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/lpproducts/**").permitAll()  // Vervanging voor antMatchers()
//                            .anyRequest().authenticated()
//                    );
//            return http.build();
//        }

}
