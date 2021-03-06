package com.zxj.community.mapper;

import com.zxj.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("Insert into question(title,description,gmt_create,gmt_modified,creator_id,tag) values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creatorId}, #{tag})")
    void create(Question question);

    @Select("Select * from question limit #{offset}, #{pageSize}")
    List<Question> findList(@Param("offset")Integer offSet, @Param("pageSize")Integer pageSize);

    //查询问题总个数
    @Select("Select count(1) from question")
    Integer count();

    @Select("select * from question where creator_id = #{userId} limit #{offset}, #{pageSize}")
    List<Question> listByUserId(@Param("userId")Long userId, @Param("offset")Integer offSet, @Param("pageSize")Integer pageSize);

    @Select("Select count(1) from question where creator_id = #{userId}")
    Integer countByUserId(@Param("userId")Long userId);

    @Select("Select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
