package com.example.springpracticereactiveexamples.repositories;

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
}