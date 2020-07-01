package com.zxj.community.controller;

import com.zxj.community.mapper.QuestionMapper;
import com.zxj.community.mapper.UserMapper;
import com.zxj.community.model.Question;
import com.zxj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")//表示是get请求
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")//表示是post请求。此时携带了信息。
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title", title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";//如果不满足发布条件，直接返回发布页面，并显示错误信息
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = null;//发布之前需要判定用户是否登录
        Cookie[] cookies = request.getCookies();//先获取所有的cookie
        if(cookies != null){//增强for之前最好做非空判断
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){//找到我们想要的cookie，获取用户信息
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);//如果user存在，那么久写入session以便展示“用户姓名”；否则还是显示登陆。
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());//获取发布者的ID，需要登录才能发布，即需要登录者的id
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return  "redirect:/";
    }
}
