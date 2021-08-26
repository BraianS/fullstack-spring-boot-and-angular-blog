package com.github.braians.springblog.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TitleRequest {
    @NotNull
    @Size(max = 75)
    private String title;
}
