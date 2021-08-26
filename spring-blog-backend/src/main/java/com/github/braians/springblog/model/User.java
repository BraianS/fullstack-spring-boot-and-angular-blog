package com.github.braians.springblog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class User extends BaseEntity {
    
    private String name;
    private String username;
    private String password;
    private String email;

    @JsonBackReference
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Post> posts = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "ID"))
    private Set<Role> roles = new HashSet<>();

    public void addUser(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addRole(Role role){
        this.getRoles().add(role);
    }
 
}
