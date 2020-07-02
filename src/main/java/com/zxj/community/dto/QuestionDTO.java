package com.zxj.community.dto;

import com.zxj.community.model.User;
import lombok.Data;
/**
 * 该类是为了实现网络对象和数据库对象的关联，不使用外键关联是因为会影响性能。该对象主要在业务层执行操作，实现业务逻辑。
 * 即相当于将user对象和question对象进一步封装操作，操作对象则是questionDTO
 * 这样在创建实体类时，例如user类则不需要考虑其他类属性。
 */
@Data
public class QuestionDTO {
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
    private User user;
}
