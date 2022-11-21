package nas.springframework.springrestclientexamples.service;

import nas.springframework.springrestclientexamples.api.domain.User;

import java.util.List;
import java.util.Set;

public interface ApiService {

    List<User> getUsers(int limit);
}
