package nas.springframework.springrestclientexamples.service;

import nas.springframework.springrestclientexamples.api.domain.User;
import nas.springframework.springrestclientexamples.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

private final RestTemplate restTemplate;
private final String api_url;

    public ApiServiceImpl(RestTemplate restTemplate,@Value("${api.url}")String api_url ) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override

    public List<User> getUsers(int limit) {
        UriComponentsBuilder uriBuilder=UriComponentsBuilder
                //bring it from where?
                .fromUriString(api_url)
                //sending parameters to uri
                .queryParam("limit",limit);

        //UserData.class says that in which class we want Data to Bind to
        //code review: using uricomponentBuilder, following line is the first code and the other one is the reviewed one
        //UserData userData=restTemplate.getForObject("https://dummyjson.com/users?limit=" +limit,UserData.class);
        UserData userData=restTemplate.getForObject(uriBuilder.toUriString(),UserData.class);
        return userData.getUsers();
    }
}
