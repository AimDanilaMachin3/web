package com.example.web.repos;


import com.example.web.conf.ent.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findRoleByRole(String name);
}
