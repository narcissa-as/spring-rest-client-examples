package nas.springframework.springrestclientexamples.service;

import nas.springframework.springrestclientexamples.api.domain.User;
import nas.springframework.springrestclientexamples.api.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
@Service
public class ApiServiceImpl implements ApiService {

private final RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override

    public List<User> getUsers(int limit) {
        //UserData.class says that in which class we want Data to Bind to
        UserData userData=restTemplate.getForObject("https://dummyjson.com/users?limit=" +limit,UserData.class);
        return userData.getUsers();
    }
}
