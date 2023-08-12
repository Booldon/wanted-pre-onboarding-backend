package wanted_backend.assignment.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import wanted_backend.assignment.domain.Member;
import wanted_backend.assignment.service.MemberService;
import wanted_backend.assignment.service.MemberServiceInterface;

import java.io.IOException;
import java.util.List;

@PropertySource("classpath:jwt.properties")
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        // Header의 Authorization 값이 비어있으면 Jwt Token을 전송X
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader;
        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행
        if(JwtProvider.isExpired(token, secretKey)){
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 loginId 추출
        String loginEmail = JwtProvider.getLoginEmail(token,secretKey);

        // 추출한 loginId에서 member찾기
        Member loginMember = memberService.findByEmail(loginEmail);

        // loginMember 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginMember.getEmail(), null, List.of(new SimpleGrantedAuthority(loginMember.getMemberRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);



    }


}
