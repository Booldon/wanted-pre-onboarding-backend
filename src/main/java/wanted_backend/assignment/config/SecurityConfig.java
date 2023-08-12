package wanted_backend.assignment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import wanted_backend.assignment.domain.MemberRole;
import wanted_backend.assignment.service.MemberService;
import wanted_backend.assignment.service.MemberServiceInterface;

@PropertySource("classpath:jwt.properties")
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    @Value("${secret-key}") String secretKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(new JwtTokenFilter(memberService,secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) ->
                requests.requestMatchers(mvcMatcherBuilder.pattern("/post/edit**"),mvcMatcherBuilder.pattern("/post/delete**")).hasAnyAuthority(MemberRole.User.name())
                        .requestMatchers(mvcMatcherBuilder.pattern("/"),mvcMatcherBuilder.pattern("/user/**")).permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

}
