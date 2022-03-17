package com.otopark.business.configuration.jwt;

import com.otopark.business.repository.UserJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.List.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    //private final UserRepo userRepo;
    private final UserJPARepository userJPARepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserJPARepository userJPARepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userJPARepository = userJPARepository;
    }

    //@Override
    public void doFilterInternal2(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        logger.info("JwtTokenFilter : doFilterInternal");
        String token = request.getHeader("Authorization");
        if (token != null) {
            logger.info("token var");
        } else {
            logger.info("token yok");
        }
        filterChain.doFilter(request, response);
    }

    /*
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/api/v1/auth/login", request.getServletPath());
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        logger.info("token:"+ token);
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        /*
        UserDetails userDetails = userRepo
                .findByUsername(jwtTokenUtil.getUsername(token))
                .orElse(null);

         */
        UserDetails userDetails = userJPARepository.findByUsername(jwtTokenUtil.getUsername(token)).orElse(null);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
        );

        authentication
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}