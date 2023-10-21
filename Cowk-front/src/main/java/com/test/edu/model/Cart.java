package com.test.edu.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("user")
	private User user;
	@JsonManagedReference("cart")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "cart")
	private List<Item> items;
	private float total_bill;
	@JsonManagedReference("cart1")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "cart")
	private List<Order> orders;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public float getTotal_bill() {
		return total_bill;
	}
	public void setTotal_bill(float total_bill) {
		this.total_bill = total_bill;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
