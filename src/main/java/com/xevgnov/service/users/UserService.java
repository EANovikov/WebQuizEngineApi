package com.xevgnov.service.users;


import com.xevgnov.model.users.User;

import java.util.List;

public interface UserService {

    void createUser(User user);

    User findUserByEmail(String user);

    List<User> findAllUsers();
}
