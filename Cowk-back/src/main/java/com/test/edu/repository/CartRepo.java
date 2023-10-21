package com.test.edu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	@Query(value = "select * from cart where user_id=?1", nativeQuery = true)
	Cart findbyUserID(int id);

	
	@Transactional
	@Modifying
    @Query(value = "delete from cart where user_id=?1", nativeQuery = true)
	Cart deleteByUserID(int id);

}
