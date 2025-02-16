package com.midbenchers.controller;



import com.midbenchers.dto.LoginAuthentication;
import com.midbenchers.dto.RegisterUser;
import com.midbenchers.entity.User;
import com.midbenchers.repository.Userrepo;
import com.midbenchers.service.auth.RegisterUserServiceimpl;
import com.midbenchers.utilli.Jwtutill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ecom")
public class LoginAuthenController {


    private final AuthenticationManager manager;

    private final UserDetailsService service;

    private final Userrepo userrepo;

    private final Jwtutill jwtutill;

    public final String  Token_Prefix="Bearer ";

    public static String Header_String="Authorization";


    private static final Logger log=LoggerFactory.getLogger(LoginAuthenController.class);

    @Autowired
    private RegisterUserServiceimpl regservice;

    public LoginAuthenController(AuthenticationManager manager, UserDetailsService service, Userrepo userrepo, Jwtutill jwtutill) {
        this.manager = manager;
        this.service = service;
        this.userrepo = userrepo;
        this.jwtutill = jwtutill;
    }


    @PostMapping("/authentication")
    public ResponseEntity<Map<String, Object>> generateToken(@RequestBody LoginAuthentication dto) {
        try {
            // Authenticate user
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Incorrect user/password"));
        }

        // Load user details
        final UserDetails userDetails = service.loadUserByUsername(dto.getEmail());
        Optional<User> optionalUser = userrepo.findByEmail(userDetails.getUsername());

        // Generate JWT token
        final String jwt = jwtutill.generatetoken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userid", optionalUser.get().getId());
            responseBody.put("role", optionalUser.get().getRole());
            responseBody.put("token", jwt); //
            System.out.println("roles: "+optionalUser.get().getRole());
            System.out.println(" token: "+jwt);

            return ResponseEntity.ok().body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "User not found"));
    }


    @PostMapping("/reg")
    public ResponseEntity<?>registeruser(@RequestBody RegisterUser registerUser){

        if(registerUser==null){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        System.out.println("CALLING THE USER");
        return regservice.createuser(registerUser);
    }





}
