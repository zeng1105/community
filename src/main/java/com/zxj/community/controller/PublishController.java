package com.zxj.community.controller;

import com.zxj.community.dto.QuestionDTO;
import com.zxj.community.mapper.QuestionMapper;
import com.zxj.community.model.Question;
import com.zxj.community.model.User;
import com.zxj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")//表示是get请求
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")//表示是post请求。此时携带了信息。
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id", required = false) Long id,
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

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());//获取发布者的ID，需要登录才能发布，即需要登录者的id
        question.setId(id);
        questionService.createOrUpdate(question);
        return  "redirect:/";
    }

    /**
     * 进入问题编辑页面
     * @return
     */
    @GetMapping("/publish/{id}")//
    public String edit(@PathVariable(name = "id")Long id,
                       Model model){

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
}
