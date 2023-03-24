package com.application.wallet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    public String validateJwtAndGetUserName(String jwt) throws Exception{


        if(jwt == null)
            throw new Exception("Unauthenticated !");
        // Jwt Util class
        Claims claim=null;
        String name=null;
        try{
            claim = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(jwt).getBody();
            name = claim.getIssuer();

        }
        catch (ExpiredJwtException e){
            throw new Exception("JWT got Expired please log in again.");

        }
        catch (SignatureException e){
            throw new Exception("JWT Signature Exception.");
        }
        catch (Exception e){
            throw  new Exception("Unauthenticated !");
        }
        return name;
    }
}
