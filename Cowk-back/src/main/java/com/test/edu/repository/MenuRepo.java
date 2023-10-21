package com.test.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.Menu;

public interface MenuRepo extends JpaRepository<Menu, Integer> {

	@Query(value = "select * from menus  where restaurent_id=?1", nativeQuery = true)
	Menu findByRestaurentId(int id);

}
