package com.example.springrestclientexamples.services;

import com.example.api.domain.User;
import com.example.api.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(int limit) {
        UserData userData = restTemplate
                .getForObject("http://private-8d06f-apifaketory.apiary-mock.com/api/user?limit=" + limit,
                        UserData.class);

        return userData.getData();
    }
}
