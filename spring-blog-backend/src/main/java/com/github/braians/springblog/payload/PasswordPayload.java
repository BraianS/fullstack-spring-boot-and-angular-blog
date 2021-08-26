package com.github.braians.springblog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordPayload {

    @ApiModelProperty(notes = "New Password of User",required = true,example = "braian123")
    @NotBlank
    @Size(min = 6,max = 20)
    private String oldPassword;

    @ApiModelProperty(notes = "New Password of User",required = true,example = "braian123")
    @NotBlank
    @Size(min = 6,max = 20)
    private String newPassword;

    @ApiModelProperty(notes = "New Password of User",required = true,example = "braian123")
    @NotBlank
    @Size(min = 6,max = 20)
    private String confirmPassword;
}