package com.github.braians.springblog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmailPayload {

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
