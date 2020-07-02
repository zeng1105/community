package com.zxj.community.model;

import lombok.Data;

@Data//利用lombok通过注解直接生成set/get方法，无须每次手动添加
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
