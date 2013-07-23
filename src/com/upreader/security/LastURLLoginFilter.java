package com.upreader.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Saves the last url before login page
 */
@WebFilter(urlPatterns = "/*")
public class LastURLLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String path = (String) req.getSession().getAttribute("com.caucho.servlet.login.path");
            String query = (String) req.getSession().getAttribute("com.caucho.servlet.login.query");
            if(path != null || query != null) {
                req.getSession().setAttribute("com.upreader.previous.path", path);
                req.getSession().setAttribute("com.upreader.previous.query", query);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
