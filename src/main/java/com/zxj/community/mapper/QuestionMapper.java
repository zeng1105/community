package com.zxj.community.mapper;

import com.zxj.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question(title,description,gmt_create,gmt_modified,creator_id,tag) values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creatorId}, #{tag})")
    void create(Question question);
}
