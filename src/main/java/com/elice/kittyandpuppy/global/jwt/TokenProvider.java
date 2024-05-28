package com.elice.kittyandpuppy.global.jwt;

import com.elice.kittyandpuppy.module.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * 토큰을 생성한다.<br>
     *
     * @param member 사용자 객체
     * @param expiredAt 유효기간 ex)Duration.ofHours(2)
     *
     * @throws IllegalArgumentException 잘못된 유효기간을 입력한 경우 발생<br>
     *                                  - "유효기간은 양수이어야 합니다."
     *
     * @return String token
     */
    public String generateToken(Member member, Duration expiredAt) {

        if (expiredAt.isNegative() || expiredAt.isZero()) {
            throw new IllegalArgumentException("유효기간은 양수이어야 합니다.");
        }

        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)      // 발급 시간
                .setExpiration(expiry)  // 토큰 만료 시간
                .setSubject("token")  // 토큰 용도 명시
                // Playload에 담길 Claim 값
                .claim("id", member.getId())
                .claim("email",member.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 토큰의 유효성을 검사한다.
     *
     * @param token
     * @return 토큰이 유효하면 true, 유효하지 않으면 false
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            log.info("token is valid");
            return true;
        } catch (Exception e) {
            log.info("token is invalid");
            return false;
        }
    }

    /**
     * 토큰 Claim에 저장되어 있는 사용자 id를 가져온다.
     *
     * @param token
     * @return memberId
     */
    public Long getMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }
    public String getMemberEmail(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
    /*
    *   헤더에 Token 설정
    */
    public void setTokenHeader(HttpServletResponse response, String token){
        response.setHeader("token",token);
    }

    public Optional<String> extractToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("token"));
    }
}
