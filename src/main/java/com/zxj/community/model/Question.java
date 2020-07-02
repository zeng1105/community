package com.zxj.community.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creatorId;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
}
