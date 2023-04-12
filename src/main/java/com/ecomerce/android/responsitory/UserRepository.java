package com.ecomerce.android.responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.ecomerce.android.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, String>, QueryByExampleExecutor<User>{
	Optional<User> getByEmail(String email);

}