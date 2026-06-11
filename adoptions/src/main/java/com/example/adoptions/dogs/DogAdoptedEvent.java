package com.example.adoptions.dogs;

import org.springframework.modulith.events.Externalized;

//@Externalized ("messageChannels")
public record DogAdoptedEvent (int dogId) {
}
