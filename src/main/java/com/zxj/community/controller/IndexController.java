package com.zxj.community.controller;

import com.zxj.community.dto.PaginationDTO;
import com.zxj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")//访问根路径
    public String index(Model model,
                        //定义分页变量，页数和每页容量
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize){
        //要在首页查看人们提出的问题，应当在返回之前就将需要的信息查询出来，并在页面显示。显示利用model即可
        PaginationDTO paginationDTO = questionService.findList(page,pageSize);//这样在业务层操作的questionList即包含question信息，又包含user信息。
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }
}
