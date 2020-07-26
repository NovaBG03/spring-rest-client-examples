package com.example.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateExample {

    private static final String ROOT_API = "https://api.predic8.de/shop";
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
    }

    @Test
    public void getCategories() {
        String url = ROOT_API + "/categories/";

        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);

        System.out.println("Categories");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() {
        String url = ROOT_API + "/customers/";

        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);

        System.out.println("Customers");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomer() {
        String url = ROOT_API + "/customers/";

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Ivan");
        postMap.put("lastname", "Georgiev");

        JsonNode savedNode = restTemplate.postForObject(url, postMap, JsonNode.class);

        System.out.println("Customers");
        System.out.println(savedNode.toString());
    }

    @Test
    public void updateCustomer() {
        String url = ROOT_API + "/customers/";

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Ivan");
        postMap.put("lastname", "Georgiev");

        JsonNode savedNode = restTemplate.postForObject(url, postMap, JsonNode.class);

        System.out.println("Customers");
        System.out.println(savedNode.toString());

        String customerUrl = savedNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];

        System.out.println("Customer ID: " + id);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("firstname", "Ivan 2");
        updateMap.put("lastname", "Georgiev 2");

        restTemplate.put(url + id, updateMap);

        JsonNode updatedNode = restTemplate.getForObject(url + id, JsonNode.class);

        System.out.println("Updated Customers");
        System.out.println(updatedNode.toString());
    }

    @Test(expected = ResourceAccessException.class)
    public void updateCustomerUsingPatchFailing() {
        String url = ROOT_API + "/customers/";

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Ivan");
        postMap.put("lastname", "Georgiev");

        JsonNode savedNode = restTemplate.postForObject(url, postMap, JsonNode.class);

        System.out.println("Customers");
        System.out.println(savedNode.toString());

        String customerUrl = savedNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];

        System.out.println("Customer ID: " + id);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("firstname", "Ivan 2");
        updateMap.put("lastname", "Georgiev 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        JsonNode updatedNode = restTemplate.patchForObject(url + id, entity, JsonNode.class);

        System.out.println("Updated Customers");
        System.out.println(updatedNode.toString());
    }

    @Test()
    public void updateCustomerUsingPatch() {
        String apiUrl = ROOT_API + "/customers/";

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Ivan");
        postMap.put("lastname", "Georgiev");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Ivan 2");
        postMap.put("lastname", "Georgiev 2");

        //setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        System.out.println(updatedNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() {
        String postUrl = ROOT_API + "/customers/";

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Ivan");
        postMap.put("lastname", "Gerogiev");

        JsonNode savedCustomer = restTemplate.postForObject(postUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(savedCustomer.toString());

        String customerUrl = savedCustomer.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];

        System.out.println("Customer ID: " + id);

        String deleteUrl = postUrl + id;
        restTemplate.delete(deleteUrl);

        restTemplate.getForObject(deleteUrl, JsonNode.class);
    }
}
