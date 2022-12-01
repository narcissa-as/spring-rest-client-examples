package nas.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {
    private final String API_ROOT = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories() throws Exception {

        String apiUri = API_ROOT + "/categories/";
        RestTemplate restTemplate = new RestTemplate();

        //getting JSON object
        JsonNode jsonNode =
                restTemplate.getForObject(apiUri, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCostumers() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();
        // JsonNode is an object that represents structured JSON within the jackson library
        // and it is a handy way to get generic json
        JsonNode jsonNode =
                restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomer() throws Exception {
        String apiUri = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Jon");
        postMap.put("lastname", "Thompson");

        JsonNode jsonNode = restTemplate.postForObject(apiUri, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode);
    }

    @Test
    public void updateCustomer() throws Exception {

        //creat a customer to update then
        String apiUri = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Jon");
        postMap.put("lastname", "Thompson");

        JsonNode jsonNode = restTemplate.postForObject(apiUri, postMap, JsonNode.class);
        //getting the specific Id from json Response of Creating Object, like following line
        //{"firstname":"Jon","lastname":"Thompson","customer_url":"/shop/customers/349"}

        String customerUrl = jsonNode.get("customer_url").textValue();

        //split response to string array and get the third array which is the customer id
        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "susan");
        postMap.put("lastname", "Thompson");

        restTemplate.put(apiUri + id, postMap);
        //restTemplate.put(apiUri, updateMap);
        JsonNode jsonUpdatedNode = restTemplate.getForObject(apiUri + id, JsonNode.class);

        System.out.println("Updated Response");
        System.out.println(jsonUpdatedNode.toString());

    }
    //due to sun.net.www.protocol.http.HttpURLConnection not supporting patch HTTP request,we got errorI/O error
    //in the updateCustomerUsingPatch method in this Class we resolved this error,
    @Test(expected = IOException.class)
    public void updateCustomerUsingPatchSunHttp() throws Exception {
        //create a new Customer
        String apiUri = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Create a customer object
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Jon");
        postMap.put("lastname", "Thompson");

        JsonNode jsonNode = restTemplate.postForObject(apiUri, postMap, JsonNode.class);

        System.out.println("Customer created");
        System.out.println(jsonNode.toString());

        //Fetching Id of Customer to Patch
        String customerUri = jsonNode.get("customer_url").textValue();
        String id = customerUri.split("/")[3];

        System.out.println("Created Customer Id is: " + id);

        //Updating Customer
        postMap.put("firstname", "jon2");
        postMap.put("lastname", "Thompson2");

        JsonNode updatedNode = restTemplate.patchForObject(apiUri + id,postMap, JsonNode.class);
        System.out.println(updatedNode.toString());

    }
    //only works with httpclient from apache httpComponent
    @Test
    public void updateCustomerUsingPatch() throws Exception {
        //create a new Customer
        String apiUri = API_ROOT + "/customers/";

        // Use Apache HTTP client factory: we got error in the updateCustomerUsingPatchSunHttp() method  because The HTTPUriConnection doesn't support "Patch" HTTP request
        //it's from Sun company, we have another HTTPClientLibrary(s) that we use the Apache one here wired in
        // RestTemplate Bean, so we could have Patch request too
        //see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        HttpComponentsClientHttpRequestFactory requestFactory= new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);//wiring in HTTPComponent library to restTemplate

        //Create a customer object
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Jon");
        postMap.put("lastname", "Thompson");

        JsonNode jsonNode = restTemplate.postForObject(apiUri, postMap, JsonNode.class);

        System.out.println("Customer created");
        System.out.println(jsonNode.toString());

        //Fetching Id of Customer to Patch
        String customerUri = jsonNode.get("customer_url").textValue();
        String id = customerUri.split("/")[3];

        System.out.println("Created Customer Id is: " + id);

        //Updating Customer
        postMap.put("firstname", "jon2");
        postMap.put("lastname", "Thompson2");

        //Example of setting headers
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap);
        //Patch for Update
        //fails if we don't use the wiring above the method,due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        JsonNode updatedNode = restTemplate.patchForObject(apiUri + id, entity, JsonNode.class);
        System.out.println("Customer Updated");
        System.out.println(updatedNode.toString());

    }

    @Test(expected =HttpClientErrorException.class) // adding this expected part is for when we expect an error and the test should get the Error as result
    public void deleteCustomer() throws Exception {
        //Create a new Customer
        String apiUri = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Jon");
        postMap.put("lastname", "Thompson");

        JsonNode jsonNode = restTemplate.postForObject(apiUri, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode);


        //Fetching the Id, we want to delete from jsonNode Response
        String customerUri = jsonNode.get("customer_url").textValue();
        String id = customerUri.split("/")[3];

        System.out.println("Created customer id is: " + id);

        //Delete customer
        restTemplate.delete(apiUri + id);//expects 200 status
        System.out.println("Customer deleted");

        //checking the result
        //should go kaboom on 404
        restTemplate.getForObject(apiUri + id, JsonNode.class);

    }
}
