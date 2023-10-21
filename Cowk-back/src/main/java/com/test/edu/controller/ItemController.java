package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Item;
import com.test.edu.model.Menu;
import com.test.edu.repository.ItemRepo;
import com.test.edu.repository.MenuRepo;
import com.test.edu.repository.RestaurentRepo;

@RestController
@CrossOrigin
@RequestMapping("api/item")
public class ItemController {
	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private MenuRepo menuRepo;
	@Autowired
	private RestaurentRepo resRepo;
	
	
	
	@RequestMapping("/additem/{name}/{price}/{type}/{catagory}/{discount}/{menu_id}")
	public ResponseEntity<Item> createItem(@PathVariable("name") String name,
			@PathVariable("price") float price,@PathVariable("type") String type,
			@PathVariable("catagory") String catagory,
			@PathVariable("discount")  float discount,@PathVariable("menu_id")  int menu_id){
		Item item=new Item();
		Menu menu=menuRepo.getById(menu_id);
		item.setName(name);
		item.setPrice(price);
		item.setDiscount(discount);
		item.setCatagory(catagory);
		item.setType(type);
		item.setMenu(menu);
		if(discount!=0) {
			float discount_amount=((price*discount)/100);
			item.setFinal_price(price-discount_amount);
		}else {
			item.setFinal_price(price);
		}
		Item i=itemRepo.save(item);
		if(i!=null) {
			return ResponseEntity.ok(i);
		}else
		    return ResponseEntity.badRequest().body(i);
	}
	
	@RequestMapping("/updateitem/{name}/{price}/{type}/{catagory}/{discount}/{id}")
	public ResponseEntity<String> updateItem(@PathVariable("name") String name,
			@PathVariable("price") float price,@PathVariable("name") String type,@PathVariable("catagory") String catagory,
			@PathVariable("discount")  float discount,@PathVariable("id")  int id){
		int i=itemRepo.updateItembyId(name,price,type,catagory,discount,id);
		if(i!=0) {
			return ResponseEntity.ok().body("item updated");
		}
		return ResponseEntity.badRequest().body("failed to update item");
	}
	
	@RequestMapping("/deleteitem/{id}/{menu_id}")
	public ResponseEntity<String> deleteItem(@PathVariable("id") int id,@PathVariable("menu_id") int menu_id){
		int i=itemRepo.deleteItemfromMenu(id,menu_id);
		if(i!=0) {
			return ResponseEntity.ok().body("deleted");
		}
		return ResponseEntity.badRequest().body("not deleted");
	}
	
	@RequestMapping("/getitem/{id}")
	public ResponseEntity<Item> getItem(@PathVariable("id") int id){
		Item i=itemRepo.findById(id).get();
		if(i!=null) {
			return ResponseEntity.ok().body(i);
		}
		return ResponseEntity.badRequest().body(i);
	}
	
	
}
