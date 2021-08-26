package com.github.braians.springblog.projection;

import java.time.LocalDateTime;

public interface CommentSummary {
    Long getId();
    String getContent();
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getUpdatedAt();
    String getUpdatedBy();
    UserInfo getUser();
}
