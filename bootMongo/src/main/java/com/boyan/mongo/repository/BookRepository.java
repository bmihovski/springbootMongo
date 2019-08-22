package com.boyan.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, Long>{
	public Book findByTitle(String title);
	public List<Book> findByType(String type);
	public List<Book> findByAuthor(String author);
	
	@Query("{ 'type' : ?0 }")
	public List<Book> findByBookType(String type);
	@Query("{ 'type' : {$ne : ?0} }")
	public List<Book> findByBookTypeNot(String type);
	
}
