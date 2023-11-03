package com.ning.interceptor;

import com.alibaba.fastjson.JSON;
import com.ning.HttpStatusCode;
import com.ning.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
@Component
public class WebInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = request.getHeader("token");
        String id =request.getHeader("id");
        System.out.println("id="+id+"\r\ntoken="+userToken);
        String token = redisTemplate.opsForValue().get(id);
        if(id !=null && userToken!=null && token != null && (token.equals(userToken))){
            return true;
        }else {
            ResponseResult responseResult=new ResponseResult();
            responseResult.setCode(HttpStatusCode.TOKEN_NOT_EXIST.getCode());
            responseResult.setMessage(HttpStatusCode.TOKEN_NOT_EXIST.getMessage());
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            String json= JSON.toJSONString(responseResult);
            try{
                out=response.getWriter();
                out.println(json);
                return false;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                if(out!=null) {
                    out.close();
                }
            }
        }
    }
}
