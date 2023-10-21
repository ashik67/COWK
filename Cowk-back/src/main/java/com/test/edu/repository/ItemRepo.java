package com.test.edu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

	@Transactional
	@Modifying
    @Query(value = "update items set name=?1,price=?2,type=?3,catagory=?4,discount=?5 where id=?6", nativeQuery = true)
	int updateItembyId(String name, float price, String type, String catagory, float discount, int id);

	@Query(value = "select * from items where menu_id=?1", nativeQuery = true)
	List<Item> findAllByMenu_id(int id);

	@Transactional
	@Modifying
    @Query(value = "delete from items where id=?1 and menu_id=?2", nativeQuery = true)
	int deleteItemfromMenu(int id, int menu_id);

}
