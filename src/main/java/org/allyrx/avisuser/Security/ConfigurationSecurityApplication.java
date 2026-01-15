package org.allyrx.avisuser.Security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity @AllArgsConstructor
public class ConfigurationSecurityApplication {

    //injection du jwtfilter
    jwtFilter jwtFilter;
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

                        )
                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //on met en stateless le coolies pour que le cote front va geree tous
                        .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class) //cette ligne qui occupe le token(verifier s'il existe , pas expirer ,..)
                        .build();
    }


    //crypter le mot de passe
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //confuguration d'authentification manager pour la connexion
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
