package com.xevgnov.repository.users;

import com.xevgnov.model.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByEmail(String email);

    List<User> findAll();

}
