package com.boyan.mongo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootMongoApplication implements CommandLineRunner {

	private BookRepository repository;
	
	@Autowired
	public BootMongoApplication(BookRepository repository) {
		this.repository = repository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BootMongoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();
		System.out.println("Collection deleted");
		repository.save(new Book("A Tale Of Two Cities",
				"Charles Dickens", "Novel", 10));
		repository.save(new Book("The Da Vinci Code", "Dan Brown", "thriller", 12));
		repository.save(new Book("Think and Grow Rich", "Napoleon Hill", "Motivational", 10));
		repository.save(new Book("Le Petit Prince", "Antoine de Saint-Exupery", "Novel", 8));
		System.out.println("Book found with findAll():");
		System.out.println("--------------------------------");
		repository.findAll().stream()
			.forEach(System.out::println);
		System.out.println();
		System.out.println("Book found with findByTitle('The Da Vinci Code'):");
	    System.out.println("--------------------------------");
	    Book book1 = repository.findByTitle("The Da Vinci Code");
	    book1.setPrice(5);
	    // Update book
	    repository.save(book1);
	    System.out.println(repository.findByTitle("The Da Vinci Code"));
	    // Delete book
	    repository.delete(repository.findByTitle("The Da Vinci Code"));
	    
	    System.out.println("Book found with findByType('Novel'):");
	    System.out.println("--------------------------------");
	    repository.findByType("Novel").stream()
	    	.forEach(System.out::println);
	}

}
