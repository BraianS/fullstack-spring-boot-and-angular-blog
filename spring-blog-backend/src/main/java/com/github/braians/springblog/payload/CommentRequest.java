package com.github.braians.springblog.payload;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequest {
    
    @ApiModelProperty(notes = "Content of comment")
    @Size(max = 4000)
    private String content;
}
