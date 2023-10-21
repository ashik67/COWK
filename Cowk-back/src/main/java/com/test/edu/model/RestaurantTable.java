package com.test.edu.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name="restaurent_tables")
public class RestaurantTable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int table_no;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonBackReference("res4")
	@JoinColumn(name="restaurent_id")
	private Restaurant restaurent;
	//@JsonManagedReference("rt")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "restaurentTable")
	private Set<Order> orders;
	private String Status;
	@JsonManagedReference("rt")
	@OneToMany(cascade = CascadeType.MERGE)
	private  List<RestaurantUser> waiters;
	
	
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	

	
	public List<RestaurantUser> getWaiters() {
		return waiters;
	}
	public void setWaiters(List<RestaurantUser> waiters) {
		this.waiters = waiters;
	}
	public int getTable_no() {
		return table_no;
	}
	public void setTable_no(int table_no) {
		this.table_no = table_no;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Set<Order> getOrder() {
		return orders;
	}
	public void setOrder(Set<Order> order) {
		this.orders = order;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Restaurant getRestaurent() {
		return restaurent;
	}
	public void setRestaurent(Restaurant restaurent) {
		this.restaurent = restaurent;
	}
	
	
}
