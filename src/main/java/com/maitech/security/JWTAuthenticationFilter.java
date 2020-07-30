package com.maitech.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationmanager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationmanager) {
        super();
        this.authenticationmanager = authenticationmanager;
        setFilterProcessesUrl(SecurityConstant.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JsonObject userJsonObject;
        String login, password;

        try {
            Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A");
            String userJson = s.hasNext() ? s.next() : "";
            userJsonObject = new Gson().fromJson(userJson, JsonObject.class);

            login = userJsonObject.get("username").getAsString();
            password = userJsonObject.get("password").getAsString();
            return authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CustomUserDetails springuser = (CustomUserDetails) authResult.getPrincipal();

        String jwt = Jwts.builder().setSubject(springuser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET)
                .claim("role", springuser.getRole())
                .compact();

        response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + jwt);
    }
}
