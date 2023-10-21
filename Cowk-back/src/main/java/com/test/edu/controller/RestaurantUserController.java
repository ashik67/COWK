package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Restaurant;
import com.test.edu.model.RestaurantUser;
import com.test.edu.repository.RestaurentRepo;
import com.test.edu.repository.RestaurentUserRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/restaurentuser")
public class RestaurantUserController {
	
	@Autowired
	RestaurentRepo restaurentRepo;
	@Autowired
	RestaurentUserRepo restaurentUserRepo;
	
	@RequestMapping("/createuser/{name}/{mobile_no}/{role}/{restaurent_id}")
	public ResponseEntity<RestaurantUser> createUserforRestaurent(@PathVariable("name") String name,
			@PathVariable("mobile_no") long mobile_no,
			@PathVariable("role") String role,@PathVariable("restaurent_id") int restaurent_id){
		Restaurant restaurent=restaurentRepo.getById(restaurent_id);
		RestaurantUser u=new RestaurantUser();
		u.setName(name);
		u.setMobile_no(mobile_no);
		u.setRole(role);
		u.setRestaurent(restaurent);
		RestaurantUser ruser=restaurentUserRepo.save(u);
		if(ruser!=null) {
			return ResponseEntity.ok(ruser);
		}else
		    return ResponseEntity.badRequest().body(ruser);
		
	}
	
	
	@RequestMapping("/updaterestaurentuser/{name}/{mobile_no}/{role}/{restaurent_id}")
	public ResponseEntity<Integer> updateUserforRestaurent(@PathVariable("name") String name,@PathVariable("mobile_no") long mobile_no,
			@PathVariable("role") String role,@PathVariable("restaurent_id") int restaurent_id){
		int i=restaurentUserRepo.updateUserforRestaurentbyId(name, mobile_no, role, restaurent_id);
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}
	
	
	
	@RequestMapping("getrestaurantuser/{mobile_no}")
	public RestaurantUser getRestaurantUser(@PathVariable("mobile_no") long mobile_no){
		//System.out.println(mobile_no);
		RestaurantUser res_user=restaurentUserRepo.getByMobile_no(mobile_no);
		if(res_user!=null)
			return res_user;
		else
			return null;
	}
	
	@RequestMapping("logged_in/{mobile_no}")
	public RestaurantUser logged_in(@PathVariable("mobile_no") long mobile_no){
		//System.out.println(mobile_no);
		RestaurantUser res_user=restaurentUserRepo.getByMobile_no(mobile_no);
		if(res_user!=null) {
			System.out.println(res_user.getName());
			res_user.setIsavailable("true");
			res_user.setIsLoggedin("true");
			res_user.setCount(0);
			restaurentUserRepo.save(res_user);
		}
		return res_user;	
	}
	
	
	@RequestMapping("logged_out/{mobile_no}")
	public RestaurantUser logged_out(@PathVariable("mobile_no") long mobile_no){
		System.out.println(mobile_no);
		RestaurantUser res_user=restaurentUserRepo.getByMobile_no(mobile_no);
		if(res_user!=null) {
			res_user.setIsavailable("false");
			res_user.setIsLoggedin("false");
			res_user.setCount(0);
			restaurentUserRepo.save(res_user);
		}
		return res_user;
	}
	
	
	
	/*@RequestMapping("/updateuserpassword/{mobile_no}/{password}")
	public ResponseEntity<Integer> updateUserPassword(@PathVariable("mobile_no") long mobile_no,@PathVariable("password") String password){
		RestaurantUser user=restaurentUserRepo.getByMobile_no(mobile_no);
		int i;
		String decrypt_pass = new String(Base64.getMimeDecoder().decode(user.getPassword()));
		if(password.equals(decrypt_pass))
			return ResponseEntity.badRequest().body(0);
		else {
			password=Base64	.getEncoder().encodeToString(password.getBytes());
			i=restaurentUserRepo.updatePassword(password,mobile_no);
		}
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}*/
	
	@RequestMapping("/deleteuser/{id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable("id") int id){
		int i=restaurentUserRepo.deleteUserbyId(id);
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}
	
	
	
	

}
