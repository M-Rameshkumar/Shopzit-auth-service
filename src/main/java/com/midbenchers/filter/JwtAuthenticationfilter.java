package com.midbenchers.filter;

import com.midbenchers.service.jwt.Userdetailsimpl;
import com.midbenchers.utilli.Jwtutill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationfilter extends OncePerRequestFilter {


    //step1:extends OnceperRequeestilter and methods
    //step2: add the utill and userdetails

    private final Jwtutill jwtutill;
    private final Userdetailsimpl userDetailsService;


    public JwtAuthenticationfilter(Jwtutill jwtutill, Userdetailsimpl userDetailsService) {
        this.jwtutill = jwtutill;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(req);
        if (token != null) {
            String username = jwtutill.extractusername(token);
            if (username != null && jwtutill.Validatetoken(token, username)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Token is valid and authentication set for user: " + username);
            } else {
                System.out.println("Invalid token for user: " + username);
            }
        } else {
            System.out.println("No token found in request");
        }
        filterChain.doFilter(req, res);
    }


    //extracting token and senting to filteration

    private String extractToken(HttpServletRequest req){

        String authHeader=req.getHeader("Authorization");

        return (authHeader!=null && authHeader.startsWith("Bearer "))?
                authHeader.substring(7):null;

    }
}
