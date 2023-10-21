package com.test.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

	@Query(value = "select * from orders where restaurenttable_id =?1", nativeQuery = true)
	List<Order> findOrdersbyTableId(int tid);

}
