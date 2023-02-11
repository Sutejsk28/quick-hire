package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Enums.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
        //given
        UserEntity user = new UserEntity();
        user.setUsername("sutej");
        user.setEmail("sutej@123.com");
        user.setPassword("123456789");
        user.setCreatedTime(Instant.now());
        user.setAddress("Roy road");
        user.setCity("Belagavi");
        user.setCountry("India");
        user.setRoles(Roles.USER);

        userRepository.save(user);

        //when
        UserEntity user1 = userRepository.findByUsername(user.getUsername()).orElseThrow();

        //then
        assertThat(user1).isEqualTo(user);


    }
}