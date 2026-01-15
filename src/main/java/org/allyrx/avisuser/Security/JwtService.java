package org.allyrx.avisuser.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.stereotype.Service;
import java.security.Key;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class JwtService {
    public final String SECRET_ENCRYPTION_KEY = "e0088yu8742rhv874dfv78dv7fd45f7r5t5r1t45TR5t5s5FDgfG4fg5QWqWQ4";
    private final userService userService;

    //generer un token par un email
    public Map<String , String> generateJwtToken(String email) {
        //on recupere le donees des user
        User user =  userService.loadUserByUsername(email);
        return generateJwt(user);
    }

    private Map<String, String> generateJwt(User user) {

        //Informations qu'on aime envoyer avec le token dans un json
        final  Map<String ,String> claims = Map.of(
                "email" , user.getEmail(),
                "nom" , user.getUsername()
        );
        final long currentTime = System.currentTimeMillis();
        final long expiration = currentTime + TimeUnit.MINUTES.toSeconds(30);
        String token = Jwts.builder()
                .setIssuedAt(new Date(currentTime)) //Date d'emission
                .setExpiration(new Date(expiration)) //temps d'expiration
                .setSubject(user.getEmail()) //sujet du token pour l'user
                .setClaims(claims) //informations envoyer avec le token
                .signWith(getKey() , SignatureAlgorithm.HS256) //signature du cle secrete generer par getKey((
                .compact(); //creer le token string en finale
        return  Map.of("bearer" , token);
    }

    //codage du cle secrete
    private Key getKey() {
      final byte[] decode =  Decoders.BASE64.decode(SECRET_ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);

    }

    public String ExtractUsername(String token) {
        return null;
    }

    public Boolean verifyExpiration(String token) {
        return true;
    }
}
