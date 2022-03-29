package com.example.web.repos;

import com.example.web.conf.ent.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String name);

    User findUserByUsername(String s);

}
