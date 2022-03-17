package com.otopark.presentation.controller;

import java.util.Collection;

import com.otopark.business.configuration.jwt.JwtTokenUtil;
import com.otopark.business.model.User;
import com.otopark.business.service.UserService;
import com.otopark.presentation.controller.dto.JwtResponseDTO;
import com.otopark.presentation.controller.dto.LoginRequestDTO;
import com.otopark.presentation.controller.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid; //TODO

@CrossOrigin("http://localhost:3005")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();

            String jwt = jwtTokenUtil.generateAccessToken(user);

            ResponseDTO resp = new ResponseDTO<JwtResponseDTO>("SUCCESS",
                    new JwtResponseDTO(jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(), null));
            return ResponseEntity.ok(resp);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(path = "/authorities")
    public ResponseEntity<ResponseDTO<Collection<? extends GrantedAuthority>>> userAuthorities() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        ResponseDTO<Collection<? extends GrantedAuthority>> responseDTO = new ResponseDTO<>("SUCCESS", authorities);
        logger.info(responseDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
