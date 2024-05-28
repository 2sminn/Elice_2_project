package com.elice.kittyandpuppy.global.config;

import com.elice.kittyandpuppy.global.filter.JwtFilter;
import com.elice.kittyandpuppy.global.jwt.TokenProvider;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // h2-console 화면을 사용하기 위해
                .csrf(AbstractHttpConfigurer::disable)
                .headers(
                        headersConfigurer -> headersConfigurer
                                .frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::disable
                                )
                );
        http
                .authorizeRequests((auth) -> auth
                        // Category
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                        // Product
                        .requestMatchers(HttpMethod.GET, "/api/product", "/api/product/**").permitAll()

                        // Community & Comment
                        .requestMatchers(HttpMethod.POST, "/api/community/{postId}/increment-views").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/communities", "/api/community/{id}", "/api/comment/{postId}").permitAll()

                        // Member sign-up
                        .requestMatchers(HttpMethod.POST, "/api/member").permitAll()
                        .requestMatchers("/signup", "/api/member/validation/**").permitAll()
                        .requestMatchers("/api/member/login").permitAll()

                        // OrderItem
                        .requestMatchers(HttpMethod.GET, "/api/orderItem/{id}", "/api/cart").permitAll()
                        // Order

                        // Address
                        .requestMatchers(HttpMethod.GET, "/api/address/delivery/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/address/member", "/api/details").hasAnyRole("USER", "ADMIN")

                        // Admin
                        .requestMatchers("/api/admin/**", "/admin").hasRole("ADMIN")

                        // Page
                        .requestMatchers(HttpMethod.GET, "/login", "/signup", "/mypage", "/categories", "/animal/**", "/communities", "/cart").permitAll()

                        // static 파일
                        .requestMatchers(HttpMethod.GET, "/", "/static/**", "/footer.html", "/header.html", "/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterAfter(jwtFilter(), LogoutFilter.class);
//        http.addFilterAfter(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources()
                                .atCommonLocations())
                ;
    }

    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter(tokenProvider, memberRepository);
        return jwtFilter;
    }

}
