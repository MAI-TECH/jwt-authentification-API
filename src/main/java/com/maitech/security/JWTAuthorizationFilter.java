package com.maitech.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, " + "Access-Control-Request-Method, " + "Access-Control-Request-Headers, " + "Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin," + "Access-Control-Allow-Credentials, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            String jwt = request.getHeader(SecurityConstant.HEADER_STRING);
            if (jwt == null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                filterchain.doFilter(request, response);
                return;
            }

            Claims claim = Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX, "")).getBody();
            String email = claim.getSubject();

            UsernamePasswordAuthenticationToken authenticaticateduser = new UsernamePasswordAuthenticationToken(email, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticaticateduser);
            filterchain.doFilter(request, response);
        }
    }
}