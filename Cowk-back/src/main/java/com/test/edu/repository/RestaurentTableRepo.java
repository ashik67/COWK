package com.test.edu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.RestaurantTable;

public interface RestaurentTableRepo extends JpaRepository<RestaurantTable, Integer> {

	@Transactional
	@Modifying
    @Query(value = "delete from restaurent_tables where id=?1", nativeQuery = true)
	int deleteByTableId(int tid);

	@Query(value = "select * from restaurent_tables  where restaurent_id=?1 and  table_no=?2", nativeQuery = true)
	RestaurantTable findByTable_no(int rid, int tid);

	@Query(value = "select * from restaurent_tables  where restaurent_id=?1 and status='available'", nativeQuery = true)
	List<RestaurantTable> findAvailableTables(int id);

	@Query(value = "select * from restaurent_tables  where restaurent_id=?1", nativeQuery = true)
	List<RestaurantTable> findByrid(int r_id);

}
