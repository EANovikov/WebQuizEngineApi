package com.xevgnov.service.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Test
    public void loadUserByUsernameReturnsCorrectUserDetailsTest() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user1@test.com");
        assertThat(userDetails.getUsername()).isEqualTo("user1@test.com");
        assertThat(userDetails.getPassword()).isEqualTo("$2a$10$RRNGgJUeR4czObEY17ul5emHSomUZ3v2RInJx0uCPiAelfso9JEf2");
        assertThat(userDetails.getAuthorities().isEmpty()).isFalse();
        assertThat(userDetails.getAuthorities().toString()).contains("ROLE_USER");
    }

    @Test
    public void loadUserByNonExistingUsernameReturnsUsernameNotFoundExceptionTest() throws Exception {
        assertThatThrownBy(() -> {
            userDetailsService.loadUserByUsername("user123@test.com");
        }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("user123@test.com");

    }
}