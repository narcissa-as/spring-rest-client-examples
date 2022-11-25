package nas.springframework.springrestclientexamples.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//indicates that a class declares one or more @Bean methods, so Spring Container can process the class
// and generates Spring Bean to be used in the application
@Configuration
public class RestTemplateConfig {

        /*Guru didn't new the RestTemplateBuilder() and just return that like this:
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder){
            return builder.build();
        }
        maybe because in Guru's version we need to just Autowire the RestTemplateBuilder as a parameter, but now
        because of the version or s.th else, if we don't new RestTemplateBuilder()
        we get a red line under this line, so I search and found that we should new RestTemplateBuilder() too*/

    /*in following lines we could have a Bean of restTemplate by Autowiring from Spring like a BeanFactory*/
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
    // since the restTemplate method is dependent to RestTemplateBuilder, we need to add this also as a Bean
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
