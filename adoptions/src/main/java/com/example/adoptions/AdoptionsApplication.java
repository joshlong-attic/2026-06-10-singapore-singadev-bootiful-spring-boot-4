package com.example.adoptions;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.springaicommunity.mcp.security.client.sync.config.McpClientOAuth2Configurer.mcpClientOAuth2;

@EnableResilientMethods
@SpringBootApplication
public class AdoptionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionsApplication.class, args);
    }

}



/*
@Component
class CatFactsClient {

    private final RestClient http;

    CatFactsClient(RestClient.Builder http) {
        this.http = http.build();
    }

    CatFacts facts() {
        return this.http
                .get()
                .uri("https://www.catfacts.net/api")
                .retrieve()
                .body(CatFacts.class);
    }
}
*/

//@Import(MyBeanRegistrar.class)
@Configuration
class MyConfiguration {

//    @Scheduled(fixedDelay = 1000)
//    void runner(IncompleteEventPublications eventPublications) {
//        eventPublications
//                .resubmitIncompletePublications((e) -> true);
//    }
}

class MyBeanRegistrar implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {
        for (var i = 0; i < 10; i++) {
            var name = "runner" + i;
            registry.registerBean(MyRunner.class, a -> a.supplier(supplierContext -> new MyRunner(name)));
        }
    }
}

class MyRunner implements ApplicationRunner {

    private final String name;

    MyRunner(String name) {
        this.name = name;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IO.println("hi, " + name + "!");
    }
}

@Configuration
class SecurityConfiguration {

    @Bean
    Customizer<HttpSecurity> httpSecurityCustomizer() {
        return http -> http.with(mcpClientOAuth2());
    }
}