package com.github.braians.springblog.model;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BaseSlugEntity extends AuditEntity {
   
    private String slug;
}
