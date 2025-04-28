package com.example.springpracticereactiveexamples.repositories;

import com.example.springpracticereactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;

class PersonRepositoryImplTest {

	PersonRepository personRepository = new PersonRepositoryImpl();

	@Test
	void test_mono_get_by_id_block() {
		// This test is checking the functionality of the findById method in the PersonRepositoryImpl class.
		// It creates a Mono object that represents a single asynchronous value (a Person object in this case).
		// The test then blocks the Mono to get the actual Person object and prints the result.
		// The test is expected to pass if the findById method works correctly and returns a Person object with the specified ID.

		var person_mono = personRepository.findById(1);

		System.out.println(
			person_mono.block()
		);
	}

	/*
	 * The test method named `test_mono_get_by_id_subscribe()` demonstrates how to work with a `Mono` in a reactive programming context.
	 * A `Mono` is a Reactor type that represents a single asynchronous value or an empty result.
	 *
	 * In this test, the `findById` method of the `PersonRepository` is called to retrieve a `Mono` containing a `Person` object with the specified ID (`1` in this case).
	 * Instead of blocking the `Mono` to retrieve the value (as in the other test), this method uses the `subscribe` method to handle the result asynchronously.
	 * The `subscribe` method takes a lambda function as a parameter, which is executed when the `Mono` emits its value.
	 *
	 * This approach aligns with the non-blocking nature of reactive programming, as it does not halt the execution of the test while waiting for the `Mono` to resolve.
	 * However, since this is a test, it lacks assertions to verify the correctness of the result, making it more of a demonstration than a proper test case.
	 * */

	@Test
	void test_mono_get_by_id_subscribe() {
		var person_mono = personRepository.findById(1);

		person_mono.subscribe(person -> System.out.println(person.toString()));
	}

	@Test
	void test_map_operations() {
		// This test demonstrates the use of the `map` operator in a reactive programming context.
		// It retrieves a Mono containing a Person object with the specified ID (1) and then applies a transformation to it.
		// The transformation involves mapping the Person object to its name using the `map` operator.
		// Finally, it subscribes to the transformed Mono and prints the name of the person.

		var person_mono = personRepository.findById(1);

		person_mono
			.map(Person::firstName) // This is same as person_mono.map(person -> person.firstName())
			.subscribe(System.out::println); // This is same as person_mono.subscribe(name -> System.out.println(name));
	}

	@Test
	void test_flux_block_first() {
		// This test demonstrates how to retrieve a Flux of Person objects from the PersonRepository and block to get the first element.
		// It uses the `findAll` method to get a Flux of Person objects and then blocks to retrieve the first element using `blockFirst()`.
		// Finally, it prints the result.

		var person_flux = personRepository.findAll();

		System.out.println(
			person_flux.blockFirst()
		);
	}

}