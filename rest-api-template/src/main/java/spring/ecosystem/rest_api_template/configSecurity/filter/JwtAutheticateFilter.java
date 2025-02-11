package spring.ecosystem.rest_api_template.configSecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.services.UserService;
import spring.ecosystem.rest_api_template.services.auth.JwtService;

import java.io.IOException;

@Component
public class JwtAutheticateFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeaders = request.getHeader("Authorization");

            if (!StringUtils.hasText(authorizationHeaders) || !authorizationHeaders.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authorizationHeaders.split(" ")[1];
            String userName = jwtService.extracEmail(jwt);
            User user = userService.findOneByEmail(userName);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null,
                    user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
    }
}