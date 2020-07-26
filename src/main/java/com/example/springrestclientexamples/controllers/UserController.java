package com.example.springrestclientexamples.controllers;

import com.example.api.domain.User;
import com.example.springrestclientexamples.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/"})
    public String getIndex(Model model) {
        model.addAttribute("limit", 1);
        return "index";
    }

    @PostMapping("/users")
    public String processForm(Model model, ServerWebExchange serverWebExchange) {

        int limit = 1;

        Flux<User> users = apiService.getUsers(Mono.just(limit));

        model.addAttribute("users", users);

        return "userlist";
    }
}
