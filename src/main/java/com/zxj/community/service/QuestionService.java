package com.zxj.community.service;

import com.zxj.community.dto.PaginationDTO;
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

    public PaginationDTO findList(Integer page, Integer pageSize) {
        Integer totalCount = questionMapper.count();//问题总条数
        PaginationDTO paginationDTO = new PaginationDTO();//能操作页码信息的类
        Integer totalPage = paginationDTO.getTotalPage(totalCount,pageSize);//获取总页数
        //对查询页码数做安全判断，必须要写在查询前
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);//设置页码逻辑
        //问题信息查询
        Integer offSet = (page-1)*pageSize;//偏移量计算：limit offset,pageSize; offset = (page-1)*pageSize;
        List<Question> questions = questionMapper.findList(offSet,pageSize);
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
        paginationDTO.setQuestionDTOList(questionDTOList);//存储页面（问题）信息
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer pageSize) {
        Integer totalCount = questionMapper.countByUserId(userId);//问题总条数
        PaginationDTO paginationDTO = new PaginationDTO();//能操作页码信息的类
        Integer totalPage = paginationDTO.getTotalPage(totalCount,pageSize);//获取总页数
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);//设置页码逻辑
        //问题信息查询
        Integer offSet = (page-1)*pageSize;
        List<Question> questions = questionMapper.listByUserId(userId,offSet,pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(questions!=null){
            for (Question question : questions) {
                //通过用户id查找用户
                User user =  userMapper.findById(question.getCreatorId());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreatorId());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
