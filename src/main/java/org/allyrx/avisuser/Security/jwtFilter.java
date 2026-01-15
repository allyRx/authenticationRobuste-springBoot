package org.allyrx.avisuser.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public class jwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private userService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String requestHeader = request.getHeader("Authorization");
        Boolean isTokenExpired = true;
        //verification de token et extraction leur informations
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String token = requestHeader.substring(7);
            username = jwtService.ExtractUsername(token);
            isTokenExpired = jwtService.verifyExpiration(token);
        }


        if ( isTokenExpired != null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userService.loadUserByUsername(username);

            //on valide l'authentification
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response); //continuer de verifier les req et le res
        }

    }
}
