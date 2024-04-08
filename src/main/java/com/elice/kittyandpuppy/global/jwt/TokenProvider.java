package com.elice.kittyandpuppy.global.jwt;

import com.elice.kittyandpuppy.module.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

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
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(member.getEmail())
                .claim("id", member.getId())
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 Claim에 저장되어 있는 사용자 id를 가져온다.
     *
     * @param token
     * @return memberId
     */
    public Long getMemberrId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
