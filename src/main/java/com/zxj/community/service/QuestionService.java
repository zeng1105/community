package com.zxj.community.service;

import com.zxj.community.dto.QuestionDTO;
import com.zxj.community.mapper.QuestionMapper;
import com.zxj.community.mapper.UserMapper;
import com.zxj.community.model.Question;
import com.zxj.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> findList() {

        List<Question> questions = questionMapper.findList();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(questions!=null){
            for (Question question : questions) {
                //通过用户id查找用户
                User user =  userMapper.findById(question.getCreatorId());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);//利用Spring内置的BeanUtils将一个对象的属性快速的拷贝给另一个几乎相同的对象。
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }
        return questionDTOList;
    }
}
