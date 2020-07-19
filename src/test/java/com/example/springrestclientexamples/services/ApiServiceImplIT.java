package com.example.springrestclientexamples.services;

import com.example.api.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplIT {

    @Autowired
    ApiService apiService;

    @Test
    public void getUserTest() {
        int limit = 1;

        List<User> users = apiService.getUsers(limit);

        assertEquals(limit, users.size());
    }
}