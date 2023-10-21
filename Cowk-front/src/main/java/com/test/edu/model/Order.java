package com.test.edu.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private LocalDate date;
	private LocalTime time;
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Item> items;
	private float total_bill;
	@JsonManagedReference(value="or1")
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL,mappedBy = "order")
	private Offer offer;
	@JsonBackReference("restaurent")
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="restaurent_id")
	private Restaurant restaurent;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	//@JsonBackReference("rt")
	@JsonIgnore
	@JoinColumn(name="restaurenttable_id")
	private RestaurantTable restaurentTable;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("cart1")
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RestaurantTable getRestaurentTable() {
		return restaurentTable;
	}
	public void setRestaurentTable(RestaurantTable restaurentTable) {
		this.restaurentTable = restaurentTable;
	}
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public float getTotal_bill() {
		return total_bill;
	}
	public void setTotal_bill(float total_bill) {
		this.total_bill = total_bill;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public Restaurant getRestaurent() {
		return restaurent;
	}
	public void setRestaurent(Restaurant restaurent) {
		this.restaurent = restaurent;
	}	
	

}
