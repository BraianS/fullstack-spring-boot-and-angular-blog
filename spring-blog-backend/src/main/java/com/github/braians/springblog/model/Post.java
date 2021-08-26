package com.github.braians.springblog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Post extends BaseSlugEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String summary;

    @Enumerated(EnumType.STRING)
    private StatusName status;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.ALL})
    @JoinTable(name = "post_tag",
     joinColumns =
        @JoinColumn(name = "post_id",referencedColumnName = "ID"), 
    inverseJoinColumns =
        @JoinColumn(name="tag_id",referencedColumnName = "ID")
    )
    @JsonIgnoreProperties("posts")
    private Set<Tag>  tags = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "post_category",
        joinColumns = 
            @JoinColumn(name="post_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name="tag_id", referencedColumnName = "ID"))
    @JsonIgnoreProperties("posts")
    private Set<Category> categories = new HashSet<>();
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public void addTag(String title, String content, Set<Tag> tags, Set<Category> categories){       
        this.title =  title;
        this.content = content;
       
        this.tags = tags;
        this.categories = categories;
    }

    public void addUser(User user){
        this.user = user;
    }


}
