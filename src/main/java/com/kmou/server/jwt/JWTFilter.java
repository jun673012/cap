package com.kmou.server.jwt;

import com.kmou.server.controller.PostController;
import com.kmou.server.dto.CustomUserDetails;
import com.kmou.server.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.info("Token is not found");
            filterChain.doFilter(request, response);

            return;
        }

        logger.info("Token is found");
        String token = authorization.split(" ")[2];
        logger.info("Token: {}", token);

        if (jwtUtil.isExpired(token)) {
            logger.info("Token is expired");
            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtUtil.getUsername(token);
        String name = jwtUtil.getName(token);
        String role = jwtUtil.getRole(token);
        String phoneNumber = jwtUtil.getPhoneNumber(token);


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setName(name);
        userEntity.setPassword("tempassword");
        userEntity.setRole(role);
        userEntity.setPhoneNumber(phoneNumber);


        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
