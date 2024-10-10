package study.likelionbeweekly.week7.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.likelionbeweekly.week7.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{ //method 이름으로 컨테이너에서 찾아야 함
        return http //dispatcher suvlet 들어오기 전에 쳐내는 것
                .csrf(AbstractHttpConfigurer::disable) // 외부에서 script 넣는 공격 => csrf
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->request
                        .requestMatchers(HttpMethod.PATCH, "/members/{id}").authenticated() //내부 권한 검사
                        .requestMatchers(HttpMethod.DELETE, "/members/{id}").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement //세션의 무상태성을 위해서
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
