package nas.springframework.springrestclientexamples.api.domain;

import java.util.List;
import java.util.Set;
//since the API that we use return list of Users, we create this Equivalent domain to return list of User Objects
// from API in it
public class UserData {

    List<User> users;

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users){
        this.users=users;
    }

}
