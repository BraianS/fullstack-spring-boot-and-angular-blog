package com.github.braians.springblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppResponse {

    private Boolean success;
    private String message;

}
