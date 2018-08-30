package com.ak.test;

import com.aungkhant.model.Column;
import com.aungkhant.model.Id;
import com.aungkhant.model.Table;

@Table("people")
public class People {
	
	@Id("people_id")
	private int id;
	
	@Column("name")
	private String name;
	
	@Column("age")
	private int age;
	
	@Column("address")
	private String address;
	
	@Column("staff")
	private Boolean staff;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getStaff() {
		return staff;
	}
	public void setStaff(Boolean staff) {
		this.staff = staff;
	}
}
