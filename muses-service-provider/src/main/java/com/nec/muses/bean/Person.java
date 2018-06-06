package com.nec.muses.bean;


public class Person{
	int personId;
	String name;
	int age;
	
	public Person() {
		// NOP
	}
	
	public Person(int personId, String name, int age) {
		super();
		this.personId = personId;
		this.name = name;
		this.age = age;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}