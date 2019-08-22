package com.boyan.mongo.template;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

@Repository
public class BookDAOImpl implements BookDAO {

	private MongoOperations mongoOps;

	private final static String BOOK_COLLECTION = "Book";

	@Autowired
	public BookDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}

	public BookDAOImpl() {
	}

	@Override
	public void insert(Book p) {
		this.mongoOps.insert(p, BOOK_COLLECTION);
	}

	@Override
	public void insertAll(Book[] p) {
		this.mongoOps.insert(Arrays.asList(p), BOOK_COLLECTION);
	}

	@Override
	public Book findByTitle(String title) {
		Query query = new Query(Criteria.where("title").is(title));
		return this.mongoOps.findOne(query, Book.class, BOOK_COLLECTION);
	}

	@Override
	public void update(Book p) {
		this.mongoOps.save(p, BOOK_COLLECTION);

	}

	@Override
	public int deleteByTitle(String title) {
		Query query = new Query(Criteria.where("title").is(title));
		DeleteResult result = this.mongoOps.remove(query, Book.class, BOOK_COLLECTION);
		return (int) result.getDeletedCount();
	}

	@Override
	public void dropCollectionIfExists() {
		if (mongoOps.collectionExists(BOOK_COLLECTION)) {
			mongoOps.dropCollection(BOOK_COLLECTION);
			System.out.println("dropped collection");
		}
	}

}
