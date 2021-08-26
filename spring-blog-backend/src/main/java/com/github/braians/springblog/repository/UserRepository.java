package com.github.braians.springblog.repository;

import java.util.Optional;

import com.github.braians.springblog.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>   {
    
    Optional<User> findByUsername(@Param("username") String username);
    Optional<User> findByEmail(@Param("email") String email);
    Boolean existsByUsername(@Param("username") String username);
    Boolean existsByEmail(@Param("email") String email);
    Boolean existsByPassword(@Param("password") String password);
    Optional<User> findByUsernameOrEmail(@Param("username") String usernameOrEmail, @Param("email") String email);   
}
