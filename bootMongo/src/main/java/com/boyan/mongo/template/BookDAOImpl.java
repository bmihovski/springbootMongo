package com.boyan.mongo.template;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
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

	@Override
	public Book findByTypeAndPriceLessThan(String type, int price) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type).and("price").lt(price));
		return mongoOps.findOne(query, Book.class);
	}

	@Override
	public Book findByTypeAndPriceBetween(String type, int moreThan, int lessThan) {
		Query q = new Query();
		q.addCriteria(Criteria.where("type").is(type).andOperator(
				Criteria.where("price").gt(moreThan),
				Criteria.where("price").lt(lessThan)));
		return mongoOps.findOne(q, Book.class);
	}

	@Override
	public Book findByFantasyAndPriceLessThanTenWithBasicQuery() {
		BasicQuery query = new BasicQuery("{'price': { $lt: 10}, 'type': 'Fantasy'}");
		return mongoOps.findOne(query, Book.class);
	}

}
