package com.test.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.edu.model.Offer;

public interface OfferRepo extends JpaRepository<Offer, Integer> {

}
