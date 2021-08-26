package com.github.braians.springblog.projection;

import java.time.LocalDateTime;
import java.util.Set;


public interface CommentResponse {
    Long getId();
    String getContent();
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getUpdatedAt();
    String getUpdatedBy();
    UserInfo getUser();
    Set<CommentResponse> getComments();
}
