package com.github.braians.springblog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class SignInRequest {
    
    @ApiModelProperty(notes = "New name of User",required = true,example = "braian silva")
    @NotBlank
    @Size(min = 4,max = 50)
    private String name;

    @ApiModelProperty(notes = "New username of User",required = true,example = "braian123")
    @NotBlank
    @Size(min = 3,max = 15)
    private String username;

    @ApiModelProperty(notes = "New Password of User",required = true,example = "braian123")
    @NotBlank
    @Size(min = 6,max = 20)
    private String password;

    @ApiModelProperty(notes = "New email of User",required = true,example = "braian123@gmail.com")
    @Email
    @NotBlank
    @Size(max = 40)
    private String email;
}
