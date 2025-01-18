package com.example.userservice.config;

import com.example.userservice.application.MemberService;
import com.example.userservice.dto.MemberDto;
import com.example.userservice.dto.RequestLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final MemberService memberService;
    private final Environment env;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager, MemberService memberService, Environment env) {
        super(authenticationManager);
        this.memberService = memberService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLoginDto requestLoginDto =
                    new ObjectMapper().readValue(request.getInputStream(), RequestLoginDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLoginDto.getEmail(),
                            requestLoginDto.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        MemberDto memberDetails = memberService.getMemberDetailsByEmail(username);

        String token = Jwts.builder()
                .setSubject(memberDetails.getMemberId())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(Objects.requireNonNull(env.getProperty("token.expiration_time")))))
                .signWith(Keys.hmacShaKeyFor(Objects.requireNonNull(env.getProperty("token.secret")).getBytes()))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", memberDetails.getMemberId());
    }
}