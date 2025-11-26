package com.okbo_projects.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.okbo_projects.common.exception.ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED;

@Slf4j(topic = "Filter")
@Component
@RequiredArgsConstructor
public class LoginCheckFilter extends OncePerRequestFilter {

    private static final Map<String, String> whitelist = new HashMap<>();
    static {
        whitelist.put("/users", "POST"); // 회원가입
        whitelist.put("/users/login", "POST"); // 로그인
        whitelist.put("/boards/board/*", "GET"); // 단건 게시글 상세 조회
        whitelist.put("/boards", "GET"); // 게시글 전체 조회
        whitelist.put("/boards/teams/*", "GET"); // 구단별 게시글 조회
        whitelist.put("/commemts/boards/*", "GET"); // 게시글별 댓글 조회
        whitelist.put("/commemts/comments-count/boards/*", "GET"); // 게시글별 댓글 수 count
        whitelist.put("/likes/like-count/boards/*", "GET"); // 게시글 좋아요 count
        whitelist.put("/likes/like-count/comments/*", "GET"); // 댓글 좋아요 count
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
                CustomException unauthorizedException = new CustomException(UNAUTHORIZED_LOGIN_REQUIRED);
                handleCustomException(response, unauthorizedException);
                log.error("CustomException 발생 : " + unauthorizedException.getErrorMessage().getMessage());
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

            if (PatternMatchUtils.simpleMatch(allowedUri, requestURI)) {
                if (allowedMethod == null) {
                    return true;
                } else if (allowedMethod.equals(requestMethod)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Filter 내부에서 발생하는 CustomException 처리
    private void handleCustomException(HttpServletResponse response, CustomException e) throws IOException {
        response.setStatus(e.getErrorMessage().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorMessage());
        writeErrorResponse(response,errorResponse);
    }
    private void writeErrorResponse(HttpServletResponse response, ErrorResponse body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}
