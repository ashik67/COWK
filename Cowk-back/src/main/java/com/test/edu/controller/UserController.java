package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.edu.model.Cart;
import com.test.edu.model.User;
import com.test.edu.repository.UserRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	private RestTemplate restTemplate = new RestTemplate();

	
	@RequestMapping("/createuser/{first_name}/{last_name}/{mobile_no}")
	public ResponseEntity<User> createUser(@PathVariable("first_name") String f_name,@PathVariable("last_name") String l_name,
			@PathVariable("mobile_no") long mobile_no){
		User user_=userRepo.getByMobile_no(mobile_no);
		if(user_!=null) {
			return ResponseEntity.badRequest().body(null);
		}else {
			User u=new User();
			u.setFirstname(f_name);
			u.setLastname(l_name);
			u.setMobile_no(mobile_no);
			u.setRole("user");
			User user=userRepo.save(u);
			restTemplate.getForObject(
					"http://localhost:8081/api/cart/createcart/" + mobile_no,
					Cart.class);
			if(user!=null) {
				return ResponseEntity.ok(user);
			}else
			    return ResponseEntity.badRequest().body(user);
		}
		
	}
	
	
	
	@RequestMapping("/updateuser/{first_name}/{last_name}/{mobile_no}/{role}/{id}")
	public ResponseEntity<Integer> updateUser(@PathVariable("first_name") String f_name,@PathVariable("last_name") String l_name,
			@PathVariable("mobile_no") long mobile_no,
			@PathVariable("role") String role,@PathVariable("id") int id){
		int i=userRepo.updateUserbyId(f_name,l_name,mobile_no,role,id);
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}
	
	
	@RequestMapping("getuser/{mobile_no}")
	public User getUser(@PathVariable("mobile_no") long mobile_no){
		System.out.println(mobile_no);
		User user=userRepo.getByMobile_no(mobile_no);
		if(user!=null)
			return user;
		else
			return null;
	}
	
	
	
	/*@RequestMapping("/updateuserpassword/{mobile_no}/{password}")
	public ResponseEntity<Integer> updateUserPassword(@PathVariable("mobile_no") long mobile_no,@PathVariable("password") String password){
		User user=userRepo.getByMobile_no(mobile_no);
		int i;
		String decrypt_pass = new String(Base64.getMimeDecoder().decode(user.getPassword()));
		if(password.equals(decrypt_pass))
			return ResponseEntity.badRequest().body(0);
		else {
			password=Base64	.getEncoder().encodeToString(password.getBytes());
			i=userRepo.updatePassword(password,mobile_no);
		}
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}*/
	
	@RequestMapping("/deleteuser/{id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable("id") int id){
		int i=userRepo.deleteUserbyId(id);
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}
	

}
