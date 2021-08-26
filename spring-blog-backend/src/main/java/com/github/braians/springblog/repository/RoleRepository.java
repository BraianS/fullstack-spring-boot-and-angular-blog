package com.github.braians.springblog.repository;

import java.util.Optional;

import com.github.braians.springblog.model.Role;
import com.github.braians.springblog.model.RoleName;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(@Param("name") RoleName name);
}
