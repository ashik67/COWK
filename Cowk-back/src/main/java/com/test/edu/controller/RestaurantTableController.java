package com.test.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Restaurant;
import com.test.edu.model.RestaurantTable;
import com.test.edu.model.RestaurantUser;
import com.test.edu.repository.RestaurentRepo;
import com.test.edu.repository.RestaurentTableRepo;
import com.test.edu.repository.RestaurentUserRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/tables")
public class RestaurantTableController {
	@Autowired
	RestaurentTableRepo restaurentTableRepo;
	@Autowired
	RestaurentRepo restaurentRepo;
	@Autowired
	RestaurentUserRepo restaurentUserRepo;
	
	
	@RequestMapping("/createtables/{restaurent_id}/{no_of_tables}")
	public ResponseEntity<String> createTables(@PathVariable("restaurent_id") int rid,@PathVariable("no_of_tables") int n_tables){
		Restaurant restaurent=restaurentRepo.getById(rid);
		for(int i=1;i<=n_tables;i++) {
			RestaurantTable table=new RestaurantTable();
			table.setTable_no(i);
			table.setStatus("available");
			table.setRestaurent(restaurent);
			restaurentTableRepo.save(table);
		}
		return ResponseEntity.ok("tables created");
	}
	
	@RequestMapping("/addtables/{restaurent_id}/{no_of_tables}")
	public ResponseEntity<String> addTables(@PathVariable("restaurent_id") int rid,@PathVariable("no_of_tables") int n_tables){
		Restaurant restaurent=restaurentRepo.getById(rid);
		int len=restaurent.getRestaurentables().size();
		System.out.println(len);
		for(int i=len+1;i<=len+n_tables;i++) {
			RestaurantTable table=new RestaurantTable();
			table.setTable_no(i);
			table.setStatus("available");
			table.setRestaurent(restaurent);
			restaurentTableRepo.save(table);
		}
		return ResponseEntity.ok("tables created");
	}
	

	@RequestMapping("/deletetables/{restaurent_id}/{no_of_tables}")
	public ResponseEntity<String> deleteTable(@PathVariable("restaurent_id") int rid,@PathVariable("no_of_tables") int n_tables){
		Restaurant restaurent=restaurentRepo.getById(rid);
		int len=restaurent.getRestaurentables().size();
		for(int i=len;i<=len+n_tables;i--) {
			RestaurantTable table=restaurentTableRepo.findByTable_no(rid,i);
			restaurentTableRepo.deleteByTableId(table.getId());
		}
		return ResponseEntity.ok("tables deleted");
		
	}
	
	@RequestMapping("/gettables/{restaurent_id}")
	public ResponseEntity<List<RestaurantTable>> gettables(@PathVariable("restaurent_id") int r_id){
		List<RestaurantTable> tables=restaurentTableRepo.findByrid(r_id);
		return ResponseEntity.ok(tables);
	}
	
	@RequestMapping("/reservetable/{restaurent_id}/{table_no}")
	public ResponseEntity<RestaurantTable> reserveTable(@PathVariable("restaurent_id") int r_id,@PathVariable("table_no") int t_id){
		System.out.println("reserve table");
		RestaurantTable table=restaurentTableRepo.findByTable_no(r_id, t_id);
		return ResponseEntity.ok(table);
		
	} 
	
	
	
	@RequestMapping("/getavailabletables/{restaurent_id}")
	public ResponseEntity<List<RestaurantTable>> getStatus(@PathVariable("restaurent_id") int r_id){
		Restaurant restaurent=restaurentRepo.findById(r_id).get();
		List<RestaurantTable> tables=restaurentTableRepo.findAvailableTables(restaurent.getId());
		return ResponseEntity.ok(tables);
	}
	
	
	

}
