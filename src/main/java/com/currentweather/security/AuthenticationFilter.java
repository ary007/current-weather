package com.currentweather.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

import static com.currentweather.security.AuthenticationService.AUTH_TOKEN_HEADER_NAME;

public class AuthenticationFilter extends GenericFilterBean {
    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    RequestMatcher customFilterUrl = new AntPathRequestMatcher("/api/weather/**");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (customFilterUrl.matches(httpServletRequest)) {
            try {
                Authentication authentication = AuthenticationService.getAuthentication(httpServletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exp) {
                logger.error("Authentication error for request %s".formatted(httpServletRequest.getRequestURI())
                             +", with API key %s".formatted(httpServletRequest.getHeader(AUTH_TOKEN_HEADER_NAME))
                             +". Error message "+ exp.getMessage());
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(exp.getMessage());
                writer.flush();
                writer.close();
            }
        }
        filterChain.doFilter(request, response);

    }
}
