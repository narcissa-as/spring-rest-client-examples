package nas.springframework.springrestclientexamples.controllers;

import lombok.extern.slf4j.Slf4j;
import nas.springframework.springrestclientexamples.api.domain.UserData;
import nas.springframework.springrestclientexamples.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
@Slf4j
@Controller
public class UserController {
   private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }
    //A page with only one numeric control to get a number as a limit that is number of Users that API return
    @GetMapping({"/index","","/"})
    public String index(){
        return "index";
    }

    @PostMapping("/users")
    //we used to use the @Request parameter that is a spring MVC, but Now we need to get the "data form" by
    //using  ServerWebExchange, so we get the limit of API results from the form data
    public String showUsersList(Model model, ServerWebExchange serverWebExchange){
        //Unfortunately in spite of many searches,we have error on this line that we cant solve it out, so we cant get the output of API
        // in this commit
        MultiValueMap<String,String>map =serverWebExchange.getFormData().block();
        Integer limit=Integer.valueOf(map.get("limit").get(0));
        log.debug("Recieved limit value: " + limit);
        //default if null or zero
        if(limit==null|| limit==0){
            log.debug("Setting limit top default of 10");
            limit=10;
        }
        model.addAttribute("users",apiService.getUsers(limit));
        return "userslist";
    }
}
