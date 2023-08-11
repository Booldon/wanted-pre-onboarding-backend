package wanted_backend.assignment.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@PropertySource("classpath:jwt.properties")
@Service
public class JwtProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;
    public JwtProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-hours}") long expirationHours,
            @Value("${issuer}") String issuer
    ) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
    }

    public String createToken(String userEmail) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName())) // HS512 알고리즘을 사용하여 secretKey 적용
                .setSubject(userEmail) // JWT 토근 제목
                .setIssuer(issuer) // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS))) // 만료 시간
                .compact(); // 토근 생성
    }

}
