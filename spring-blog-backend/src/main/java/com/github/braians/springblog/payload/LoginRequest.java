package com.github.braians.springblog.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    
    @ApiModelProperty(notes = "Username or Email of User",required = true,example =  "brian123")
    @NotBlank
    private String usernameOrEmail;

    @ApiModelProperty(value = "Password of User",required = true,example =  "brian123")
    @NotBlank
    private String password;
}
