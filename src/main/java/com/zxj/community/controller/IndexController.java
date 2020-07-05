package com.zxj.community.controller;

import com.zxj.community.dto.PaginationDTO;
import com.zxj.community.mapper.UserMapper;
import com.zxj.community.model.User;
import com.zxj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")//访问根路径
    public String index(HttpServletRequest request,
                        Model model,
                        //定义分页变量，页数和每页容量
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize){

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
        //要在首页查看人们提出的问题，应当在返回之前就将需要的信息查询出来，并在页面显示。显示利用model即可
        PaginationDTO paginationDTO = questionService.findList(page,pageSize);//这样在业务层操作的questionList即包含question信息，又包含user信息。
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }
}
