package com.zxj.community.controller;

import com.zxj.community.mapper.UserMapper;
import com.zxj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")//访问根路径
    public String index(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();//先获取所有的cookie
        if(cookies != null){//增强for之前最好做非空判断
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){//找到我们想要的cookie
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);//如果user存在，那么久写入session以便展示“我”；否则还是显示登陆。
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
