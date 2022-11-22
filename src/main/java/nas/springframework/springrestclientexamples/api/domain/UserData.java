package nas.springframework.springrestclientexamples.api.domain;

import java.util.List;
import java.util.Set;

public class UserData {

    List<User> users;

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users){
        this.users=users;
    }

}
