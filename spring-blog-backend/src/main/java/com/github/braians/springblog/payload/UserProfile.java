package com.github.braians.springblog.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserProfile {
    
    private Long id;
    private String name;
    private String username;
    private LocalDateTime createdAy;
}
