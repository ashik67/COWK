package com.test.edu.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity	
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String lastname;
	private long mobile_no;
	private String role;
	@JsonManagedReference("user")
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL,mappedBy = "user")
	private Cart cart;
	
	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "user")
	private Set<Order> orders; 
	


	public Set<Order> getOrders() {
		return orders;
	}


	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public long getMobile_no() {
		return mobile_no;
	}


	public void setMobile_no(long mobile_no) {
		this.mobile_no = mobile_no;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	

}
