package com.boyan.mongo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends MongoRepository<Book, Long>{
	public Book findByTitle(@Param("title") String title);
	public List<Book> findByType(@Param("type") String type);
	public List<Book> findByAuthor(@Param("author") String author);
	
	@Query("{ 'type' : ?0 }")
	public List<Book> findByBookType(String type);
	@Query("{ 'type' : {$ne : ?0} }")
	public List<Book> findByBookTypeNot(String type);
	
}
