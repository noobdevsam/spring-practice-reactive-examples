package com.example.springpracticereactiveexamples.repositories;

import com.example.springpracticereactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person michael = new Person(1, "Michael", "Jordan");
    Person john = new Person(2, "John", "Doe");
    Person jane = new Person(3, "Jane", "Doe");
    Person alice = new Person(4, "Alice", "Doe");

    @Override
    public Mono<Person> findById(Integer id) {
        return findAll().filter(person -> person.id().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, john, jane, alice);
    }
}
