package com.boyan.mongo.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	BookDAO bookDAO;

	@Override
	public void run(String... args) throws Exception {
		bookDAO.dropCollectionIfExists();
		Book b = new Book("A Tale of Two Cities", "Charles Dickens", "Novel", 18);
		bookDAO.insert(b);
		Book[] books = new Book[] {new Book("The Da Vinci Code", "Dan Brown", "thriller", 12),
				new Book("Think and Grow Ritch", "Napoleon Hill", "thriller", 10),
				new Book("The Hobbit", "J.R.R. Tolkien", "thriller", 8),
				new Book("Le petit Prince", "Antoine de Saint-Exupery", "Fantasy", 8)
		};
		bookDAO.insertAll(books);
		Book b1 = bookDAO.findByTitle("The Hobbit");
		System.out.println("Retrieved Book=" + b1);
		b1.setPrice(6);
		
		bookDAO.update(b1);
		
		Book b2 = bookDAO.findByTitle("The Hobbit");
		System.out.println("Retrieved Book after update=" + b2);
		int count = bookDAO.deleteByTitle("Think and Grow Ritch");
		System.out.println("Number of records deleted=" + count);
		Book b3 = bookDAO.findByTypeAndPriceLessThan("Novel", 15);
		System.out.println("Retrieved book based on criteria search:" + b3);
		Book b4 = bookDAO.findByTypeAndPriceBetween("Novel", 4, 24);
		System.out.println("Retrieved book based on criteria price between search:" + b4);
		System.out.println("Retrieved book based on basic query:" + bookDAO.findByFantasyAndPriceLessThanTenWithBasicQuery());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
