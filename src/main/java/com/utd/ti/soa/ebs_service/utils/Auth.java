package com.utd.ti.soa.ebs_service.utils;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Auth {
    public final String SECRET_KEY = "aJksd9QzPl+sVdK7vYc/L4dK8HgQmPpQ5K9yApUsj3w";

    public boolean validToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 
    
            Claims claims = Jwts.parserBuilder() 
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", "")) // Eliminar el prefijo "Bearer "
                .getBody();
    
            // Aquí extraemos el nombre del usuario del "subject"
            String username = claims.getSubject(); 
            if (username != null) {
                System.out.println("Token válido, usuario: " + username);
            } else {
                System.out.println("Token válido");
            }
            
            return true;
        } catch (Exception e) {
            System.out.println("Error al validar el token: " + e.getMessage());
            return false;
        }
    }
}