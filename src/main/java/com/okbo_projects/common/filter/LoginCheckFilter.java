package com.okbo_projects.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckFilter extends OncePerRequestFilter {

    private static final Map<String, String> whitelist = new HashMap<>();
    static {
        whitelist.put("/users/login", "POST");
        whitelist.put("/users", "POST");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if(requestURI.contains("/boards") && request.getMethod().equals("GET")) {
            if(requestURI.contains("/myboard") || requestURI.contains("/followers")) {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("loginUser") == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    log.error(requestURI + " : 비로그인 접근 시도");
                    return;
                }
            }
        }

        if(!compareWithWhitelist(requestURI, request.getMethod())) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("loginUser") == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                log.error(requestURI + " : 비로그인 접근 시도");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    // whitelist와 URI 대조
    private boolean compareWithWhitelist(String requestURI, String requestMethod) {
        for (Map.Entry<String, String> listedURI : whitelist.entrySet()) {
            String allowedUri = listedURI.getKey();
            String allowedMethod = listedURI.getValue();

            if (allowedUri.equals(requestURI)) {
                if (allowedMethod == null) {
                    return true;
                } else if (allowedMethod.equals(requestMethod)) {
                    return true;
                }
            }
        }
        return false;
    }
}
