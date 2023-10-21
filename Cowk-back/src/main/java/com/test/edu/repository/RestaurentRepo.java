package com.test.edu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.Restaurant;

public interface RestaurentRepo extends JpaRepository<Restaurant, Integer> {
	
	@Transactional
	@Modifying
    @Query(value = "update restaurents set name=?1,location=?3 where id=?5", nativeQuery = true)
	int updateRestaurentbyId(String name, String location, int id);
	
	@Transactional
	@Modifying
    @Query(value = "delete from restaurents where id=?1", nativeQuery = true)
    int deleteRestaurentById(int id);

}
