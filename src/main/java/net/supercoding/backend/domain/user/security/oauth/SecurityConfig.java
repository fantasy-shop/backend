package net.supercoding.backend.domain.user.security.oauth;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.security.CustomUserDetailsService;
import net.supercoding.backend.domain.user.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발 편의용)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/users/signup",
                                "/users/login",
                                "/item",
                                "/item/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll() // 회원가입, 로그인, 정적리소스는 누구나 접근 가능
                        .anyRequest().authenticated() // 나머지는 인증 필요
//                        .anyRequest().permitAll() // 개발을 위해 임시로 모두 접근 가능
                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/users/login")
//                        .failureHandler((request, response, exception) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getWriter().write("Login Failed: " + exception.getMessage());
//                        }) // 에러 핸들러
//                        .defaultSuccessUrl("/item")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/users/logout")
//                        .logoutSuccessUrl("/users/login")
//                        .permitAll()
//                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 현재 수동으로 로그인을 검증하고 있기에 필요가 없음.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 쿠키 포함 허용
        config.addAllowedOriginPattern("*"); // 프론트엔드 주소
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
