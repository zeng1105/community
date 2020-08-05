package com.zxj.community.mapper;

import com.zxj.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //第一个id是自增不需要写入
    @Insert("Insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    @Select("Select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//当传入对象不是定义的类对象，需要加上@Param

    //这里传入question对象的creatorId，因为就是user类的id。
    @Select("Select * from user where id = #{id}")
    User findById(@Param("id") Long creatorId);

    @Select("Select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("Update user set name = #{name}, token = #{token}, gmt_create = #{gmtCreate}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
