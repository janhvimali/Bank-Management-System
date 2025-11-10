package com.quantafic.JWTSecurity.Config;

import com.quantafic.JWTSecurity.Service.CustomerService;
import com.quantafic.JWTSecurity.Service.JWTService;
import com.quantafic.JWTSecurity.Service.MyUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String id = null;
        String role = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7).trim();
                id = jwtService.extractUserName(token);
                role = jwtService.extractRole(token);
            }

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;

                if (role != null) {
                    userDetails = context.getBean(MyUserService.class).loadUserByUsername(id);
                } else {
                    userDetails = context.getBean(CustomerService.class).loadUserByUsername(id);
                }

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            handleJwtException(response, "JWT token has expired. Please log in again.");
        } catch (SignatureException ex) {
            handleJwtException(response, "Invalid JWT signature.");
        } catch (MalformedJwtException ex) {
            handleJwtException(response, "Malformed JWT token.");
        } catch (Exception ex) {
            handleJwtException(response, "Invalid or expired JWT token.");
        }
    }

    private void handleJwtException(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":401,\"success\":false,\"message\":\"" + message + "\"}");
    }
}
