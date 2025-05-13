package org.allyrx.avisuser.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

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
        Map<String ,String> claim = Map.of(
                "email" , user.getEmail(),
                "nom" , user.getUsername()
        );
        String token = Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) //Date d'emission
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //temps d'expiration
                .subject(user.getEmail()) //sujet du token pour l'user
                .claims(claim) //informations envoyer avec le token
                .signWith(getKey()) //signature du cle secrete generer par getKey((
                .compact(); //creer le token string en finale
        return  Map.of("Bearer" , token);
    }

    //codage du cle secrete
    private Key getKey() {
//      final byte[] decode =  Decoders.BASE64.decode(SECRET_ENCRYPTION_KEY);
//        return Keys.hmacShaKeyFor(decode);
      final byte[] decode =  Base64.getDecoder().decode(SECRET_ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec( decode, "HmacSHA256");
    }
}
