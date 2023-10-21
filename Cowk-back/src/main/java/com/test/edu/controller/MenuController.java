package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Menu;
import com.test.edu.model.Restaurant;
import com.test.edu.repository.ItemRepo;
import com.test.edu.repository.MenuRepo;
import com.test.edu.repository.RestaurentRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/menu")
public class MenuController {
	
	@Autowired
	MenuRepo menuRepo;
	@Autowired
	RestaurentRepo restaurentRepo;
	@Autowired
	ItemRepo itemRepo;
	
	@RequestMapping("/createmenu/{id}")
	public ResponseEntity<Menu> createmenu(@PathVariable("id") int restaurent_id){
		Restaurant restaurent=restaurentRepo.getById(restaurent_id);
		Menu menu=new Menu();
		menu.setRestaurent(restaurent);
		Menu m=menuRepo.save(menu);
		restaurent.setMenu(menu);
		restaurentRepo.save(restaurent);
		if(m!=null)
			return ResponseEntity.ok(m);
		else
			return ResponseEntity.badRequest().body(m);
		
	}
	
	@RequestMapping("/getmenu/{res_id}")
	public ResponseEntity<Menu> getMenu(@PathVariable("res_id") int id){
		Menu menu=menuRepo.findByRestaurentId(id);
		return ResponseEntity.ok(menu);
	}
	
	
	
	
	
	
	
	

}
