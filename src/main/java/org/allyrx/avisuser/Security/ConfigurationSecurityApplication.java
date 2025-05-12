package org.allyrx.avisuser.Security;


import org.allyrx.avisuser.Repository.userRepository;
import org.allyrx.avisuser.Service.userService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurityApplication {

    //Parametre de l'authorisation des routes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(
                                auth -> auth
                                        .requestMatchers(HttpMethod.POST , "/inscription").permitAll()
                                        .requestMatchers(HttpMethod.POST , "/activation").permitAll()
                                        .requestMatchers(HttpMethod.POST , "/login").permitAll()
                                        .anyRequest().authenticated()

                        ).build();
    }

    //crypter le mot de passe
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //confuguration d'authentification manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    //database acces Object pur l'authentification avec l'user detail service et le motdepasse crypter
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //affectation du deteail service
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //affectaion du mot de passe crypter
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return  daoAuthenticationProvider;
    }
}
