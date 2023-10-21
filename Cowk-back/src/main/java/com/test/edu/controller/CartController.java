package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Cart;
import com.test.edu.model.User;
import com.test.edu.repository.CartRepo;
import com.test.edu.repository.UserRepo;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private UserRepo userRepo;

	@RequestMapping("/createcart/{mobile_no}")
	public ResponseEntity<Cart> createCart(@PathVariable("mobile_no") long mobile_no) {
		User user = userRepo.getByMobile_no(mobile_no);
		if (user != null) {
			Cart cart = new Cart();
			cart.setUser(user);
			Cart c = cartRepo.save(cart);
			user.setCart(c);
			userRepo.save(user);
			if (c != null)
				return ResponseEntity.ok(c);
			else
				return ResponseEntity.badRequest().body(null);
		} else
			return ResponseEntity.badRequest().body(null);
	}


	
	@RequestMapping(value="/updatecart",method = RequestMethod.POST)
	public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {

		Cart c = cartRepo.save(cart);
		if (c != null)
			return ResponseEntity.ok(c);
		else
			return ResponseEntity.badRequest().body(null);
	}
	
	
	@RequestMapping("/getcart/{mobile_no}")
	public ResponseEntity<Cart> getCart(@PathVariable("mobile_no") long mobile_no){
		User user = userRepo.getByMobile_no(mobile_no);
		if(user!=null) {
			Cart cart=cartRepo.findbyUserID(user.getId());
			return ResponseEntity.ok(cart);
		}else
			return ResponseEntity.badRequest().body(null);
	}
	
	@RequestMapping("/deletecart/{mobile_no}")
	public ResponseEntity<String> deleteCart(@PathVariable("mobile_no") long mobile_no){
		User user = userRepo.getByMobile_no(mobile_no);
		if(user!=null) {
			cartRepo.deleteByUserID(user.getId());
			return ResponseEntity.ok("cart deleted");
		}else
			return ResponseEntity.badRequest().body("cart not deleted");
	}

}
