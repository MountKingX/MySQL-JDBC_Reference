package com.kangmin.app.mapper;

import com.kangmin.app.model.User;
import com.kangmin.app.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        userMapper.insert(new User("aa1", "a123456", UserRole.NORMAL));
        userMapper.insert(new User("bb1", "b123456", UserRole.ADMIN));
        userMapper.insert(new User("cc1", "b123456", UserRole.ADMIN));
        assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void testQuery() {
        List<User> users = userMapper.getAll();
        System.out.println(users.toString());
    }
}
