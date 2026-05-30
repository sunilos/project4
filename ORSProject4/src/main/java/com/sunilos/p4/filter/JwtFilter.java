package com.sunilos.p4.filter;

import com.sunilos.p4.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet filter that enforces JWT-based authentication on all
 * {@code /rest/*} endpoints.
 * <p>
 * For every incoming request the filter:
 * <ol>
 * <li>Checks whether the request path is publicly accessible (login, signup,
 *     forgotpassword). If so, the request passes through without token validation.</li>
 * <li>Reads the {@code Authorization} header and expects the value to follow
 *     the {@code Bearer <token>} scheme.</li>
 * <li>Validates the token via {@link JwtUtil#validateToken}. On success the
 *     decoded {@link Claims} are stored as a request attribute named
 *     {@code "claims"} so downstream controllers can read user identity.</li>
 * <li>Returns {@code 401 Unauthorized} JSON if the header is absent, malformed,
 *     or the token is invalid/expired.</li>
 * </ol>
 *
 * <p>Usage in a controller:
 * <pre>
 * Claims claims = (Claims) request.getAttribute("claims");
 * long userId  = JwtUtil.getUserId(claims);
 * long roleId  = JwtUtil.getRoleId(claims);
 * String login = JwtUtil.getLogin(claims);
 * </pre>
 *
 * @author Sunil Sahu
 * @version 1.0
 */
@WebFilter(urlPatterns = "/rest/*")
public class JwtFilter implements Filter {

    /**
     * Path prefixes that are accessible without a JWT token.
     * The {@code /rest/auth/login} and {@code /rest/auth/signup} endpoints
     * issue tokens, so they must not be protected.
     */
    private static final String[] PUBLIC_PATHS = {
        "/rest/auth/login",
        "/rest/auth/signup",
        "/rest/auth/forgotpassword"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no initialisation required
    }

    /**
     * Validates the JWT on every {@code /rest/*} request that is not public.
     *
     * @param request  the incoming servlet request
     * @param response the servlet response
     * @param chain    the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = buildPath(httpRequest);

        // Allow public endpoints without a token
        for (String pub : PUBLIC_PATHS) {
            if (path.startsWith(pub)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Require Authorization: Bearer <token>
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(httpResponse, "Authorization header missing or malformed. Use: Bearer <token>");
            return;
        }

        String token = authHeader.substring(7).trim();
        try {
            Claims claims = JwtUtil.validateToken(token);
            // Make claims available to all downstream controllers
            httpRequest.setAttribute("claims", claims);
            chain.doFilter(request, response);
        } catch (Exception e) {
            sendUnauthorized(httpResponse, "Invalid or expired token");
        }
    }

    @Override
    public void destroy() {
        // no cleanup required
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Combines servletPath + pathInfo into a single normalised path string. */
    private String buildPath(HttpServletRequest request) {
        String servlet  = request.getServletPath();
        String pathInfo = request.getPathInfo();
        return (pathInfo != null) ? servlet + pathInfo : servlet;
    }

    /**
     * Writes a {@code 401 Unauthorized} JSON error response.
     *
     * @param response the HTTP response to write to
     * @param message  the error description
     * @throws IOException if writing fails
     */
    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().print(
            "{\"success\":false,\"result\":{\"message\":\"" + message + "\"}}"
        );
    }
}
