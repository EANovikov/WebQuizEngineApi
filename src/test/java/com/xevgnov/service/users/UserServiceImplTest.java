package com.xevgnov.service.users;

import com.xevgnov.model.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private final User user = new User("user@test.com", "user-test");

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Test
    public void createUserCreatesUserWithIdEmailAndEncryptedPasswordTest() {
        userService.createUser(user);
        User savedUser = userService.findUserByEmail(user.getEmail());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getId()).isGreaterThanOrEqualTo(2);
        assertThat(new BCryptPasswordEncoder().matches(user.getPassword(), savedUser.getPassword())).isTrue();
    }

    @Test
    public void findUserByEmailExistingUserHasValidEmailAndPasswordTest() {
        User savedUser = userService.findUserByEmail("user1@test.com");
        assertThat(savedUser.getEmail()).isEqualTo("user1@test.com");
        assertThat(savedUser.getPassword()).isEqualTo("$2a$10$RRNGgJUeR4czObEY17ul5emHSomUZ3v2RInJx0uCPiAelfso9JEf2");
    }

    @Test
    public void findUserByEmailNonExistingUserIsNullTest() {
        User savedUser = userService.findUserByEmail("user123@test.com");
        assertThat(savedUser).isNull();
    }

    @Test
    public void findAllUsersReturnsDatabaseUsersWithCorrectIdEmailPasswordTest() {
        List<User> users = userService.findAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users.get(0).getId()).isEqualTo(1);
        assertThat(users.get(1).getId()).isEqualTo(2);
        assertThat(users.get(0).getEmail()).isEqualTo("user1@test.com");
        assertThat(users.get(1).getEmail()).isEqualTo("user2@test.com");
        assertThat(users.get(0).getPassword()).isEqualTo("$2a$10$RRNGgJUeR4czObEY17ul5emHSomUZ3v2RInJx0uCPiAelfso9JEf2");
        assertThat(users.get(1).getPassword()).isEqualTo("$2a$10$uHABEw8LwFiFzuu15AN8C.86RV36EglwAODy35FAmATNAlLAf43be");
    }

}