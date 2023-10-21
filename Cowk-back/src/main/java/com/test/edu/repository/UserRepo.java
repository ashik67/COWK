package com.test.edu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.edu.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	
	
	

	@Transactional
	@Modifying
    @Query(value = "update users set first_name=?1, last_name=?2, mobile_no=?3 ,role=?4 where id=?5", nativeQuery = true)
	int updateUserbyId(String f_name, String l_name, long mobile_no, String role, int id);



	@Query(value = "select * from users  where mobile_no=?1", nativeQuery = true)
	User getByMobile_no(long mobile_no);



	@Transactional
	@Modifying
    @Query(value = "update users set password=?1 where mobile_no=?2", nativeQuery = true)
	int updatePassword(String password, long mobile_no);



	@Transactional
	@Modifying
    @Query(value = "delete from users where id=?1", nativeQuery = true)
	int deleteUserbyId(int id);



	//User findByMobile_no(long mobile_no);

}
