package com.github.braians.springblog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NameRequest {

    @ApiModelProperty(notes = "New name of User", required = true, example = "braian silva")
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

}
