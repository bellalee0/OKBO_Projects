package com.okbo_projects.common.filter;

import static com.okbo_projects.common.exception.ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okbo_projects.common.exception.CustomException;
import com.okbo_projects.common.model.LoginUser;
import com.okbo_projects.common.model.response.ErrorResponse;
import com.okbo_projects.common.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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
        whitelist.put("/comments/boards/*", "GET"); // 게시글별 댓글 조회
        whitelist.put("/comments/comments-count/boards/*", "GET"); // 게시글별 댓글 수 count
        whitelist.put("/likes/like-count/boards/*", "GET"); // 게시글 좋아요 count
        whitelist.put("/likes/like-count/comments/*", "GET"); // 댓글 좋아요 count
    }

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        CustomException unauthorizedException = new CustomException(UNAUTHORIZED_LOGIN_REQUIRED);

        if (compareWithWhitelist(requestURI, request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            handleCustomException(response, unauthorizedException);
            log.error(
                "CustomException 발생 : " + unauthorizedException.getErrorMessage().getMessage());
            return;
        }

        String jwt = authorizationHeader.substring(7);

        if (!jwtUtils.validateToken(jwt)) {
            handleCustomException(response, unauthorizedException);
            log.error(
                "CustomException 발생 : " + unauthorizedException.getErrorMessage().getMessage());
            return;
        }

        request.setAttribute("loginUser", new LoginUser(jwtUtils.getUserId(jwt)));

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
    private void handleCustomException(HttpServletResponse response, CustomException e)
        throws IOException {

        response.setStatus(e.getErrorMessage().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorMessage());
        writeErrorResponse(response, errorResponse);
    }

    private void writeErrorResponse(HttpServletResponse response, ErrorResponse body)
        throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}
