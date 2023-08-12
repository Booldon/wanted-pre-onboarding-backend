package wanted_backend.assignment.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import wanted_backend.assignment.domain.Member;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
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

    public String createToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("loginId",member.getId());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName())) // HS512 알고리즘을 사용하여 secretKey 적용
                .setSubject(member.getEmail()+member.getPassword()) // JWT 토근 제목
                .setIssuer(issuer) // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS))) // 만료 시간
                .compact(); // 토큰 생성
    }

    // Claims에서 loginId 꺼내기
    public static String getLoginEmail(String token, String secretKey) {
        return extractClaims(token, secretKey).get("loginEmail").toString();
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }
    //SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
