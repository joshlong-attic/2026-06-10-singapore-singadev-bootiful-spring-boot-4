package com.example.adoptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class AdoptionsApplicationTests {

    @Test
    void contextLoads() {

        // ArchUnit
        var am = ApplicationModules.of(AdoptionsApplication.class);
        am.verify();

        IO.println(am.toString());

        new Documenter(am).writeDocumentation();
    }

}
