package com.github.braians.springblog.projection;

import java.time.LocalDateTime;
import java.util.Set;

public interface PostDetail {
    Long getId();
    String getTitle();
    String getContent();   
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getUpdatedAt();
    String getUpdatedBy();
    Set<IdAndTitleResponse> getCategories();
    Set<IdAndTitleResponse> getTags();
    UserInfo getUser(); 
}
