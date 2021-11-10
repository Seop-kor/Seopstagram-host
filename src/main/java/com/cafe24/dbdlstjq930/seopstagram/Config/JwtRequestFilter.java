package com.cafe24.dbdlstjq930.seopstagram.Config;

import com.cafe24.dbdlstjq930.seopstagram.Service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String requestTokenCookie;
        if(httpServletRequest.getCookies() != null){
//            if(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().isPresent()){
//                String encodeToken = Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue();
//                tokenFull = URLDecoder.decode(encodeToken, "UTF-8");
//            }
            if(Arrays.stream(httpServletRequest.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().isPresent()){
                String encodeToken = Arrays.stream(httpServletRequest.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue();
                requestTokenCookie = URLDecoder.decode(encodeToken, "UTF-8");
            }else{
                requestTokenCookie = null;
            }
        }else{
            requestTokenCookie = null;
        }
//        if(httpServletRequest.getSession().getAttribute("Authorization") == null){
//            requestTokenSession = null;
//        }else{
//            requestTokenSession = httpServletRequest.getSession().getAttribute("Authorization").toString();
//        }
        String username = null;
        String jwtToken = null;
        if(requestTokenCookie != null && requestTokenCookie.startsWith("Bearer ")){
            jwtToken = requestTokenCookie.substring(7);
            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT Token has expired");
            }
        }else{
            logger.warn("Jwt Token does Not begin with Bearer String");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //headerㅂ분 고민
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}