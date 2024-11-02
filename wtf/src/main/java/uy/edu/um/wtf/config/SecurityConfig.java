package uy.edu.um.wtf.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uy.edu.um.wtf.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequests ) -> authorizeRequests
                        //   .requestMatchers("/h2-console").permitAll()
                         //   .requestMatchers("/static/**").permitAll()
                        //.requestMatchers(HttpMethod.GET, "/administrator/signup").hasRole("CLIENT")
                        //.requestMatchers(HttpMethod.POST, "/administrator/signup").hasRole("CLIENT")
                        //.requestMatchers(HttpMethod.GET, "/client/signup").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/client/signup").permitAll()
                          //  .requestMatchers("/movie/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )

                .formLogin((form) -> form
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )

                .csrf(csrf -> csrf.disable())

                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);  // Aquí utilizamos tu servicio personalizado
        authProvider.setPasswordEncoder(passwordEncoder());           // Aquí configuramos el encoder

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())     // Usamos el authentication provider
                .build();
    }



}