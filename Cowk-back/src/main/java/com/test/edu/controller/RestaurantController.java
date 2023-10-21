package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.edu.model.Menu;
import com.test.edu.model.Restaurant;
import com.test.edu.repository.MenuRepo;
import com.test.edu.repository.RestaurentRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurentRepo restaurentRepo;
	@Autowired
	MenuRepo menuRepo;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@SuppressWarnings("unused")
	@RequestMapping("/createrestaurant/{name}/{location}")
	public ResponseEntity<Restaurant> createRestaurent(@PathVariable("name") String name,
			@PathVariable("location") String location){
		Restaurant restaurent=new Restaurant();
		restaurent.setName(name);
		restaurent.setLocation(location);
		Restaurant r=restaurentRepo.save(restaurent);
		Menu m=restTemplate.getForObject("http://localhost:8081/api/menu/createmenu/"+r.getId(), Menu.class);
		Menu menu=menuRepo.findById(m.getId()).get();
		//System.out.println(m.getId());
		restaurent.setMenu(menu);
		Restaurant res=restaurentRepo.save(restaurent);
		if(r!=null) 
			return ResponseEntity.ok(res);
		else
		    return ResponseEntity.badRequest().body(res);
	}
	
	@RequestMapping("/updaterestaurant/{name}/{location}/{id}")
	public ResponseEntity<Integer> updateRestaurent(@PathVariable("name") String name,
			@PathVariable("location") String location,@PathVariable("id") int id) {
		int i=restaurentRepo.updateRestaurentbyId(name, location, id);
		if(i!=0)
		    return ResponseEntity.ok(i);
		else
			return ResponseEntity.badRequest().body(i);
	}
	
	@RequestMapping("/deleterestaurant/{id}")
	public ResponseEntity<Integer> deleteRestaurent(@PathVariable("id") int id) {
		try {
			int i=restaurentRepo.deleteRestaurentById(id);
			return ResponseEntity.ok(i);
		}catch(Exception e) {
			return null;
		}
	}
	
	@RequestMapping("/getrestaurant/{id}")
	public ResponseEntity<Restaurant> getRestaurent(@PathVariable("id") int id){
		try {
			Restaurant r=restaurentRepo.findById(id).get();
			return ResponseEntity.ok(r);
		}catch(Exception e) {
			return null;
		}
		
	}

}
