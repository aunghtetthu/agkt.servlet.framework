package com.aungkhant.model;

@Table("test")
public class Test {

	
	@Id("test_id")
	private int id;
	
	@Column("age")
	private int age;
	
	@Column("name")
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
