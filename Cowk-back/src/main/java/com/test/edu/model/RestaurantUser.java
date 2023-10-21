package com.test.edu.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="RestaurentUsers")
public class RestaurantUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String gender;
	private long mobile_no;
	private String role;
	@Value("false")
	private String isLoggedin;
	private int count;
	@Value("true")
	private String isavailable;
	
	
	public String getIsLoggedin() {
		return isLoggedin;
	}

	public void setIsLoggedin(String isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	public String getIsavailable() {
		return isavailable;
	}

	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable;
	}

	public int getCount() {
		return count;
	}
	
	public RestaurantTable getTable() {
		return table;
	}
	public void setTable(RestaurantTable table) {
		this.table = table;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonBackReference("res3")
	@JoinColumn(name="restaurent_id")
	private Restaurant restaurent;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonBackReference("rt")
	@JoinColumn(name="restaurent_table_id")
	@JsonIgnore
	private RestaurantTable table;
	
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
	public Restaurant getRestaurent() {
		return restaurent;
	}
	public void setRestaurent(Restaurant restaurent) {
		this.restaurent = restaurent;
	}

}
