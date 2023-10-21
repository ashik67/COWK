package com.test.edu.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Cart;
import com.test.edu.model.Item;
import com.test.edu.model.Order;
import com.test.edu.model.Restaurant;
import com.test.edu.model.RestaurantTable;
import com.test.edu.model.User;
import com.test.edu.repository.OrderRepo;
import com.test.edu.repository.RestaurentRepo;
import com.test.edu.repository.RestaurentTableRepo;
import com.test.edu.repository.UserRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderRepo orderRepo;
	@Autowired
	RestaurentRepo restaurentRepo;
	@Autowired
	RestaurentTableRepo restaurentTableRepo;
	@Autowired
	UserRepo userRepo;

	@PostMapping(value = "/createorder/{restaurent_id}/{table_id}/{mobile_no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> createOrder(@PathVariable("restaurent_id") int rid, @RequestBody List<Item> items,
			@PathVariable("table_id") int tid,@PathVariable("mobile_no") long mobile_no, HttpServletRequest request) {
		User user=userRepo.getByMobile_no(mobile_no);
		RestaurantTable table = restaurentTableRepo.findById(tid).get();
		if (table.getStatus().equals("Occupied")) {
			Restaurant restaurent = restaurentRepo.getById(rid);
			System.out.println(restaurent.getName());
			Order order = new Order();
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			order.setDate(date);
			order.setTime(time);
			order.setRestaurent(restaurent);
			order.setItems(items);
			order.setStatus("unverified");
			order.setRestaurentTable(table);
			order.setUser(user);
			Random random = new Random();
			String otp = String.format("%04d", random.nextInt(10000));
			System.out.println(otp);
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp);
			float total_bill = 0;
			for (Item i : items) {
				total_bill += i.getFinal_price();
			}
			order.setTotal_bill(total_bill);
			Order o = orderRepo.save(order);
			if (o != null) {
				return ResponseEntity.ok(o);
			} else
				return ResponseEntity.badRequest().body(o);
		} else
			return ResponseEntity.badRequest().body(null);

	}
	
	
	@PostMapping(value="/create-order/{restaurent_id}/{table_id}/{mobile_no}")
	public ResponseEntity<Order> create_order(@PathVariable("restaurent_id") int rid,@PathVariable("table_id") int tid,@PathVariable("mobile_no") long mobile_no, HttpServletRequest request,@RequestBody Cart cart){	
		User user=userRepo.getByMobile_no(mobile_no);
		RestaurantTable table = restaurentTableRepo.findById(tid).get();
		if (table.getStatus().equals("Occupied")) {
			Restaurant restaurent = restaurentRepo.getById(rid);
			System.out.println(restaurent.getName());
			Order order = new Order();
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			order.setDate(date);
			order.setTime(time);
			order.setCart(cart);
			order.setRestaurent(restaurent);
			order.setStatus("unverified");
			order.setRestaurentTable(table);
			order.setUser(user);
			Random random = new Random();
			String otp = String.format("%04d", random.nextInt(10000));
			System.out.println(otp);
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp);
			float total_bill = 0;
			for (Item i : cart.getItems()) {
				total_bill += i.getFinal_price();
			}
			order.setTotal_bill(total_bill);
			Order o = orderRepo.save(order);
			if (o != null) {
				return ResponseEntity.ok(o);
			} else
				return ResponseEntity.badRequest().body(o);
		} else
			return ResponseEntity.badRequest().body(null);
	}
	

	@PostMapping(value = "/updateorder/{order_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> updateOrder(@PathVariable("order_id") int o_id, @RequestBody List<Item> items,
			HttpServletRequest request) {
		Order order=orderRepo.findById(o_id).get();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		order.setDate(date);
		order.setTime(time);
		order.setItems(items);
		order.setStatus("unverified");
		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));
		System.out.println(otp);
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		float total_bill = 0;
		for (Item i : items) {
			total_bill += i.getFinal_price();
		}
		order.setTotal_bill(total_bill);
		Order o = orderRepo.save(order);
		if (o != null) {
			return ResponseEntity.ok(o);
		} else
			return ResponseEntity.badRequest().body(o);

	}

	@RequestMapping("/verifyorder/{order_id}/{otp}")
	public ResponseEntity<Order> verifyOrder(@PathVariable("order_id") int o_id, @PathVariable("otp") String otp,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String otp_ = (String) session.getAttribute("otp");
		Order order = orderRepo.findById(o_id).get();
		if (otp.equals(otp_)) {
			order.setStatus("verified");
			Order o = orderRepo.save(order);
			session.removeAttribute("otp");
			if (o != null) {
				return ResponseEntity.ok(o);
			} else
				return ResponseEntity.badRequest().body(o);
		} else
			return ResponseEntity.badRequest().body(order);
	}

	@RequestMapping("/getordersofrestaurant/{restaurent_id}")
	public ResponseEntity<Set<Order>> getOrdersofRestaurent(@PathVariable("restaurent_id") int rid) {
		Restaurant restaurent = restaurentRepo.getById(rid);
		if (restaurent != null) {
			return ResponseEntity.ok(restaurent.getOrders());
		} else
			return ResponseEntity.badRequest().body(null);

	}

	@RequestMapping("/getorder/{order_id}")
	public ResponseEntity<Order> getorder(@PathVariable("order_id") int oid) {
		Order order = orderRepo.getById(oid);
		if (order != null) {
			return ResponseEntity.ok(order);
		} else
			return ResponseEntity.badRequest().body(order);

	}

	@RequestMapping("/getordersbytableid/{table_id}")
	public ResponseEntity<List<Order>> getorderbyTableid(@PathVariable("table_id") int tid) {
		List<Order> orders = orderRepo.findOrdersbyTableId(tid);
		if (orders != null) {
			return ResponseEntity.ok(orders);
		} else
			return ResponseEntity.badRequest().body(orders);

	}

}
