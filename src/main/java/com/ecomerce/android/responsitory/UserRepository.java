package com.ecomerce.android.responsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.ecomerce.android.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, String>, QueryByExampleExecutor<User>{
	Optional<User> getByEmail(String email);
	
	@Query("select u.userName from User u where u.email=:email")
	String getUsernameByEmail(String email);
	
	@Query("select u.userName from User u")
	List<String> getAllUsername();
	
	@Query("select u.email from User u")
	List<String> getAllEmail();

}