package com.cafe24.dbdlstjq930.seopstagram.Intercept;

import lombok.extern.java.Log;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Log
public class LoginIntercept implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String tokenFull = (String) WebUtils.getSessionAttribute(request, "Authorization");
        String tokenFull = null;
        if(request.getCookies() != null){
            try{
                if(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().isPresent()){
                    String encodeToken = Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue();
                    tokenFull = URLDecoder.decode(encodeToken, "UTF-8");
                }
            }catch (NoSuchElementException e){
                e.printStackTrace();
                tokenFull = null;
            }catch (Exception e2){
                e2.printStackTrace();
                tokenFull = null;
            }
        }
//        HttpSession session = request.getSession();
//        if(session.getAttribute("Authorization") == null){
//            response.sendRedirect(request.getContextPath() + "/login");
//            return false;
//        }
        if(tokenFull == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
