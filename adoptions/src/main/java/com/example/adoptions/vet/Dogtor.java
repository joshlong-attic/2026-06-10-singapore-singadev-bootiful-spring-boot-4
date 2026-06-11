package com.example.adoptions.vet;

import com.example.adoptions.dogs.DogAdoptedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
class Dogtor {


    @ApplicationModuleListener
    void checkup(DogAdoptedEvent dogId) throws Exception {
        Thread.sleep(5000);
        IO.println("scheduling check up on dog " + dogId);
    }
}
