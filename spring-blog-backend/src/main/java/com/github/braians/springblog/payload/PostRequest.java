package com.github.braians.springblog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequest  {

    @ApiModelProperty(notes = "Title of Post",required = true)
    @NotBlank
    @Size(max = 100)
    private String title;
    @ApiModelProperty(notes = "Content of Post",required = true)
    @Size(max = 5000)
    @NotBlank
    private String content;
    @NotBlank
    @ApiModelProperty(notes = "Type of Status",required = true, example = "PUBLIC")
    private String status;

    @ApiModelProperty(notes = "List of names of tags created",example = "[\"JAVA\"]")
    @NotEmpty
    private Set<String > tags = new HashSet<>();
    
    @ApiModelProperty(notes = "List of names of categories created",example = "[\"JAVA\"]")
    @NotEmpty
    private Set<String> categories = new HashSet<>();
}
