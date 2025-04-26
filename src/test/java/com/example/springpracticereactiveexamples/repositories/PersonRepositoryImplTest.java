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
}