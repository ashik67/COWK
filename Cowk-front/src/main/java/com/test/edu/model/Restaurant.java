package com.test.edu.model;

import java.util.List;
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

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="restaurents")
public class Restaurant {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private float rating; 
	private String location;
	@JsonManagedReference("res1")
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "restaurent")
	private Menu menu;
	@JsonManagedReference("res2")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "restaurent")
	private Set<Offer> offers;
	@JsonManagedReference("restaurent")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "restaurent")
	private Set<Order> orders;
	@JsonManagedReference("res3")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "restaurent")
	private List<RestaurantUser> restaurentusers;
	@JsonManagedReference("res4")
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "restaurent")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	private Set<RestaurantTable> restaurentables;
	
	
	
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
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Set<Offer> getOffers() {
		return offers;
	}
	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Set<RestaurantTable> getRestaurentables() {
		return restaurentables;
	}
	public void setRestaurentables(Set<RestaurantTable> restaurentables) {
		this.restaurentables = restaurentables;
	}
	

}
