package com.github.braians.springblog.projection;

import java.time.LocalDateTime;
import java.util.Set;

public interface PostSummary {
    Long getId();
    String getTitle();
    String getSummary();
    String getSlug();
    LocalDateTime getCreatedAt();
    Set<IdAndTitleResponse> getCategories();
    UserInfo getUser(); 
}