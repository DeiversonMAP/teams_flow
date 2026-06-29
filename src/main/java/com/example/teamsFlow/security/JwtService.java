package com.example.teamsFlow.security;

import com.example.teamsFlow.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    private Key getSigningKey() {
        byte[] keyBytes = chaveAssinatura.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(User user) {
        long expDias = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusDays(expDias);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(Date.from(instant))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) { return false; }
    }

    public String obterEmailUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }
}
