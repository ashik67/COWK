package com.test.edu.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="items")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private float price;
	private int quantity;
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private String type;
	private String catagory;
	private float discount;
	private float final_price;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("menu")
	@JoinColumn(name="menu_id")
	private Menu menu;
	
	@ManyToMany(mappedBy = "items",cascade = CascadeType.MERGE)
	@JsonIgnore
	private Set<Order> orders;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("cart")
	@JoinColumn(name="cart_id")
	private Cart cart;
	

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public float getFinal_price() {
		return final_price;
	}

	public void setFinal_price(float final_price) {
		this.final_price = final_price;
	}


	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
