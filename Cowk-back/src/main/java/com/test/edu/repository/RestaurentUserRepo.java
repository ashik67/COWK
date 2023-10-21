package com.test.edu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.RestaurantUser;

public interface RestaurentUserRepo extends JpaRepository<RestaurantUser, Integer> {
	
	@Transactional
	@Modifying
    @Query(value = "update restaurent_users set name=?1,  mobile_no=?2 ,role=?3 where restaurent_id=?4", nativeQuery = true)
	int updateUserforRestaurentbyId(String name, long mobile_no, String role,
			int restaurent_id);

	@Query(value = "select * from restaurent_users  where mobile_no=?1", nativeQuery = true)
	RestaurantUser getByMobile_no(long mobile_no);

	@Transactional
	@Modifying
    @Query(value = "update restaurent_users set password=?1 where mobile_no=?2", nativeQuery = true)
	int updatePassword(String password, long mobile_no);

	@Transactional
	@Modifying
    @Query(value = "delete from restaurent_users  where id=?1", nativeQuery = true)
	int deleteUserbyId(int id);

	@Query(value = "select * from restaurent_users  where restaurent_id=?1 and isavailable='true' and is_loggedin='true' and role='waiter' and count=((select min(count) from restaurent_users  where is_loggedin='true' and isavailable='true') ) LIMIT 1", nativeQuery = true)
	RestaurantUser findavailablewaiters(int restaurant_id);

	@Query(value = "select * from restaurent_users  where restaurent_id=?1 and  isavailable='true' and is_loggedin='true' and role='waiter' order by RAND() LIMIT 1", nativeQuery = true)
	RestaurantUser findavailablewaitersrandom(int r_id);


}
