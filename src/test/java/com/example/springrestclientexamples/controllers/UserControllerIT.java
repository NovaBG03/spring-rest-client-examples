package com.example.springrestclientexamples.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerIT {

    @Autowired
    ApplicationContext applicationContext;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void indexTest() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void processFormTest() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("limit", "1");

        webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk();
    }
}