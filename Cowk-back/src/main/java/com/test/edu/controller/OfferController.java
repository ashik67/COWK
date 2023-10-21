package com.test.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.edu.model.Offer;
import com.test.edu.model.Order;
import com.test.edu.model.Restaurant;
import com.test.edu.repository.OfferRepo;
import com.test.edu.repository.OrderRepo;
import com.test.edu.repository.RestaurentRepo;

@RestController
@CrossOrigin
@RequestMapping("/api/offers")
public class OfferController {

	@Autowired
	private OfferRepo offerRepo;
	@Autowired
	private RestaurentRepo restaurentRepo;
	@Autowired
	private OrderRepo orderRepo;

	@RequestMapping(value = "/createoffer/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Offer> createOffer(@PathVariable("restaurant_id") int r_id, @RequestBody Offer offer) {
		Restaurant restaurant = restaurentRepo.findById(r_id).get();
		offer.setRestaurent(restaurant);
		Offer o = offerRepo.save(offer);
		if (o != null)
			return ResponseEntity.ok(o);
		else
			return ResponseEntity.internalServerError().body(o);

	}
	
	/*@RequestMapping(value = "/editoffer/{offer_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Offer> editOffer(@PathVariable("offer_id") int offer_id, @RequestBody Offer offer) {
		Offer offer_=offerRepo.findById(offer_id).get();
		offer_=offer;
		Offer o = offerRepo.save(offer_);
		if (o != null)
			return ResponseEntity.ok(o);
		else
			return ResponseEntity.internalServerError().body(o);

	}*/

	@RequestMapping("/applyoffer/{order_id}/{offer_id}")
	public ResponseEntity<Order> applyOffer(@PathVariable("order_id") int order_id,@PathVariable("offer_id") int offer_id){
		Offer offer=offerRepo.findById(offer_id).get();
		Order order=orderRepo.findById(order_id).get();
		float total_bill=order.getTotal_bill();
		float offer_discount=offer.getDiscount();
		float min_spend=offer.getMin_order();
		float max_amount=offer.getMax_amount();
		if(total_bill>min_spend) {
			float discounted_amount=(total_bill*offer_discount)/100;
			if(discounted_amount>max_amount) {
				System.out.println(max_amount);
				order.setTotal_bill(total_bill-max_amount);
				System.out.println(total_bill-max_amount);
				order.setOffer(offer);
				Order o=orderRepo.save(order);
				return ResponseEntity.ok(o);
			}else {
				order.setTotal_bill(total_bill-discounted_amount);
				order.setOffer(offer);
				Order o=orderRepo.save(order);
				return ResponseEntity.ok(o);
			}
		}else
			return ResponseEntity.badRequest().body(order);
			
	}

}
