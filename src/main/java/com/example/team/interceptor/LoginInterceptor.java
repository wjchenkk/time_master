package com.example.team.interceptor;

import com.example.team.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        boolean flg = false; // 是否通过
        String token = request.getHeader("token");
        String id=request.getHeader("id");
        String severToken=redisService.get(id);
        // 有token表示用户已登录（生产环境应该校验token合法性）
        if(token!=null&&severToken!=null&&id!=null&&severToken.equals(token)){
            redisService.setExpire(id,21600);
            flg=true;
        } else {
            // 根据系统需要，返回特定的消息格式
            write(request, response, "connect-broken");
        }
        return flg;
    }
    private void write(HttpServletRequest request, HttpServletResponse response, String content)
            throws IOException {
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(content);
    }
}
