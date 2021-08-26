package com.github.braians.springblog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tag")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Tag extends BaseSlugEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;
    
    @ManyToMany(mappedBy = "tags")
    Set<Post> posts = new HashSet<>();

    public void addTag(String title){
        this.title = title;
    }
}
