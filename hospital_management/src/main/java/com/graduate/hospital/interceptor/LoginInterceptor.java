package com.graduate.hospital.interceptor;

import com.graduate.hospital.model.DocAndUser;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        DocAndUser user=(DocAndUser) session.getAttribute("user");
        if (user!=null){
            return true;
        }

        return false;
    }
}
