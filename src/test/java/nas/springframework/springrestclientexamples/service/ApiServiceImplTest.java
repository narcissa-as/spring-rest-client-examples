package nas.springframework.springrestclientexamples.service;

import nas.springframework.springrestclientexamples.api.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApiServiceImplTest {
  @Autowired
  ApiService apiService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getUsers() {

      List<User> users=apiService.getUsers(3);
     assertEquals(users.size(), 3);

    }
}