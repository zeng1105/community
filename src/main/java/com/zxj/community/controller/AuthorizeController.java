package com.zxj.community.controller;

import com.zxj.community.dto.AccessTokenDTO;
import com.zxj.community.dto.GithubUser;
import com.zxj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    //通过在配置文件中设置固定参数，减少代码耦合性
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String Callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        //登录判断
        if(user!=null){
            //登录成功,将用户存入Session中
            request.getSession().setAttribute("user",user);
            return "redirect:/";//重定向回到首页。（不请求转发防止地址信息暴露）
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
