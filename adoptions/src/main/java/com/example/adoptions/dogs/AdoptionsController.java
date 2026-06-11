package com.example.adoptions.dogs;

import com.example.adoptions.dogs.validation.Validation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class AdoptionsController {

    private final AdoptionsService adoptionsService;

    AdoptionsController(AdoptionsService adoptionsService) {
        this.adoptionsService = adoptionsService;
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId, @RequestParam String owner) {
        this.adoptionsService.adopt(dogId, owner);
    }

}

@Service
@Transactional
class AdoptionsService {

    private final Validation validation ;

    private final DogRepository dogRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    AdoptionsService(Validation validation, DogRepository dogRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.validation = validation;
        this.dogRepository = dogRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    void adopt(int dogId, String owner) {
        this.dogRepository.findById(dogId).ifPresent(dog -> {
            var updated = this.dogRepository.save(new Dog(dog.id(), dog.name(), owner, dog.description()));
            applicationEventPublisher.publishEvent(new DogAdoptedEvent(dogId));
            IO.println("adopted " + updated);
        });

    }
}