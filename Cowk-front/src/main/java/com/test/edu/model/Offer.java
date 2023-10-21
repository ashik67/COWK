package com.test.edu.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="offers")
public class Offer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private float discount;
	private String description;
	private float min_order;
	private float max_amount;
	private LocalDate begin_date;
	private LocalTime begin_time;
	private LocalDate expire_date;
	private LocalTime expire_time;
	@JsonBackReference(value="or1")
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="order_id")
	private Order order;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference("res2")
	@JoinColumn(name="restaurent_id")
	private Restaurant restaurent;
	
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
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getMin_order() {
		return min_order;
	}
	public void setMin_order(float min_order) {
		this.min_order = min_order;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Restaurant getRestaurent() {
		return restaurent;
	}
	public void setRestaurent(Restaurant restaurent) {
		this.restaurent = restaurent;
	}
	public LocalDate getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(LocalDate begin_date) {
		this.begin_date = begin_date;
	}
	public LocalTime getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(LocalTime begin_time) {
		this.begin_time = begin_time;
	}
	public LocalDate getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(LocalDate expire_date) {
		this.expire_date = expire_date;
	}
	public LocalTime getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(LocalTime expire_time) {
		this.expire_time = expire_time;
	}
	public float getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(float max_amount) {
		this.max_amount = max_amount;
	} 
	

}
