package com.test.edu.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="menus")
public class Menu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@JsonManagedReference("menu")
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL,mappedBy = "menu")
	private List<Item> items;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("res1")
	@JoinColumn(name="restaurent_id")
	private Restaurant restaurent;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Restaurant getRestaurent() {
		return restaurent;
	}

	public void setRestaurent(Restaurant restaurent) {
		this.restaurent = restaurent;
	}	
	
	

}
