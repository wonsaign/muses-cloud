package com.nec.muses.bean;


public class Book{
	int personId;
	String name;
	String author;
	
	public Book() {
		// NOP
	}
	
	public Book(int personId, String name, String author) {
		super();
		this.personId = personId;
		this.name = name;
		this.author = author;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}