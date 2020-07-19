package com.zxj.community.controller;

import com.zxj.community.dto.PaginationDTO;
import com.zxj.community.mapper.UserMapper;
import com.zxj.community.model.User;
import com.zxj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: zeng
 * @modified By:
 */
@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize){
        User user = null;
        Cookie[] cookies = request.getCookies();//先获取所有的cookie
        if(cookies != null && cookies.length != 0){//增强for之前最好做非空判断
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){//找到我们想要的cookie
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);//如果user存在，那么久写入session以便展示“我”；否则还是显示登陆。
                    }
                    break;
                }
            }
        }
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, pageSize);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
