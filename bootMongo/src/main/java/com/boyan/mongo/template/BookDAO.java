package com.boyan.mongo.template;

public interface BookDAO {
	public void insert(Book p);
	public void insertAll(Book[] p);
	public Book findByTitle(String id);
	
	public void update(Book p);
	public int deleteByTitle(String id);
	public void dropCollectionIfExists();

}
