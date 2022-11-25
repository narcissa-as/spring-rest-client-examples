package nas.springframework.springrestclientexamples.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
//this is actually an integration test, so we run it with springRunner and SpringBootTest
//the reason that we bring up this test this way is so we get the Thymeleaf template engin wired up and set up for the testing
@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {
@Autowired
    ApplicationContext applicationContext;//bringing Application context to do Integration test
   /* public interface WebTestClient. Client for testing web servers that use WebClient internally to perform requests
    while also providing a fluent API to verify responses. This client can connect to any server over HTTP, or to a
    WebFlux application via mock request and response objects.*/
    WebTestClient webTestClient;
    @BeforeEach
    void setUp() {
        webTestClient=WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void indexTest() throws Exception{
        webTestClient.get().uri("/")
                .exchange()//invokes the API Req
                .expectStatus().isOk();

    }

    @Test
    void showUsersListTest() throws Exception{
        MultiValueMap<String,String>formData =new LinkedMultiValueMap<>();
        formData.add("limit","3");

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk();
    }
}