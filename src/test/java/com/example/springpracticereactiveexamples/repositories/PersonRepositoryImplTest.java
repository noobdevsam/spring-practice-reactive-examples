package com.example.springpracticereactiveexamples.repositories;

import com.example.springpracticereactiveexamples.domain.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	void test_flux_subscribe() {
		// This test demonstrates how to subscribe to a Flux of Person objects and print each element.
		// It uses the `findAll` method to get a Flux of Person objects and then subscribes to it.
		// The subscription prints each Person object as it is emitted by the Flux.

		var person_flux = personRepository.findAll();

		person_flux.subscribe(System.out::println);
	}

	@Test
	void test_flux_map() {
		// This test demonstrates how to use the `map` operator with a Flux of Person objects.
		// It retrieves a Flux of Person objects from the PersonRepository and then applies a transformation to it.
		// The transformation involves mapping each Person object to its first name using the `map` operator.
		// Finally, it subscribes to the transformed Flux and prints each name.

		var person_flux = personRepository.findAll();

		person_flux
			.map(Person::firstName)
			.subscribe(System.out::println);
	}

	@Test
	void test_flux_to_list() {
		// This test demonstrates how to collect a Flux of Person objects into a List.
		// It retrieves a Flux of Person objects from the PersonRepository and then collects them into a List using `collectList()`.
		// Finally, it subscribes to the List and prints each Person's first name.

		var person_flux = personRepository.findAll();

		person_flux
			.collectList()
			.subscribe(list -> {
				list.forEach(person -> System.out.println(person.firstName()));
			});

	}

	@Test
	void test_get_by_id_with_filter() {

		var person_flux = personRepository.findAll();

		// The first operation filters the Flux to find Person objects with an id equal to 3.
		// The filter operator is used to apply this condition, and the resulting filtered Flux is subscribed to using the subscribe method.
		// Each matching Person is printed to the console.
		person_flux
			.filter(person -> person.id() == 3)
			.subscribe(System.out::println);

		// The second operation filters the Flux to find the first Person object whose firstName equals "John".
		// The filter operator is again used to apply the condition, but this time the next method is called to retrieve a Mono
		// containing only the first matching element.
		// The subscribe method is then used to print the firstName of the matching Person.
		person_flux
			.filter(person -> person.firstName().equals("John"))
			.next() // This will return a Mono<Person> with the first matching element
			.subscribe(person -> {
				System.out.println(person.firstName());
			});
	}

	@Test
	void test_filter_on_name() {
		// This test demonstrates how to filter a Flux of Person objects based on their first name.
		// It retrieves a Flux of Person objects from the PersonRepository and then filters them to find those with the first name "John".
		// Finally, it subscribes to the filtered Flux and prints each matching Person.

		var person_flux = personRepository.findAll();

		person_flux
			.filter(person -> person.firstName().equals("John"))
			.subscribe(System.out::println);
	}

	@Test
	void test_find_person_by_id_not_found() {
		var person_flux = personRepository.findAll();

		final Integer id = 0;

		person_flux
			.filter(person -> person.id() == id).single()
			.doOnError(throwable -> {
				System.out.println("Error occurred in flux");
				System.out.println(throwable.toString());
			}).subscribe(
				person -> System.out.println(person.toString()),
				throwable -> {
					System.out.println("Error occurred in mono");
					System.out.println(throwable.toString());
				}
			);

		// The above code demonstrates how to handle errors in a reactive stream using the doOnError operator.
		// It retrieves a Flux of Person objects from the PersonRepository and filters it to find a Person with a specific ID (0 in this case).
		// If no matching Person is found, the single operator will emit an error.
		// The doOnError operator is used to handle the error and print a message indicating that an error occurred in the Flux.
		// The subscribe method is then used to handle both the successful case (printing the Person) and the error case (printing the error message).
		// This allows for graceful error handling in a reactive stream, ensuring that the application can respond appropriately to errors without crashing.
		// The doOnError operator is useful for logging or performing side effects when an error occurs, while the subscribe method allows for handling both success and error cases.
	}

	@Disabled
	@Test
	void test_get_by_id_found() {
		var person_mono = personRepository.findById(3);
		assertEquals(true, person_mono.hasElement().block());
	}

	@Disabled
	@Test
	void test_get_by_id_not_found() {
		var person_mono = personRepository.findById(7);
		assertEquals(false, person_mono.hasElement().block());
	}

}