package com.example.multilayercachedemo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class ActuatorAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
    {
        return !getRelativePath(request).startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        String requestKey = request.getHeader("x-custom");

        if (requestKey == null)
        {
            log.warn("Actuator key endpoint requested without/wrong key uri: {}", request.getRequestURI());
            throw new RuntimeException("UNAUTHORIZED");
        }

        log.debug("requestKey: {}", requestKey);

        setSecurityContext();
        setBasicAuth(mutableRequest);

        filterChain.doFilter(mutableRequest, response);
    }

    private void setBasicAuth(MutableHttpServletRequest mutableRequest) {
        var userPass = String.format("%s:%s", securityProperties.getUser().getName(), securityProperties.getUser().getPassword());
        String val = "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes());
        mutableRequest.putHeader("Authorization", val);
    }

    private void setSecurityContext() {
        UserPrincipal user = UserPrincipal.createSuperUser();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getRelativePath(HttpServletRequest request) {
        if (request.getPathInfo() != null) {
            return request.getPathInfo();
        }
        return request.getServletPath();
    }
}
