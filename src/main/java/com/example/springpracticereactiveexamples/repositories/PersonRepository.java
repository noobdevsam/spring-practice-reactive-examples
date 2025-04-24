package com.example.springpracticereactiveexamples.repositories;

import com.example.springpracticereactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> findById(Integer id);

    Flux<Person> findAll();
}
