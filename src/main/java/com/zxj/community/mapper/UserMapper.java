package com.zxj.community.mapper;

import com.zxj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    //第一个id是自增不需要写入
    @Insert("Insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    @Select("Select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//当传入对象不是定义的类对象，需要加上@Param
}
