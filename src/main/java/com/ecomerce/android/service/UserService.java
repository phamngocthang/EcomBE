package com.ecomerce.android.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ecomerce.android.dto.UserDTO;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.User;
import org.springframework.web.multipart.MultipartFile;


public interface UserService{

	void delete(User entity);

	public boolean checkLogin(User user);

	UserDTO findById(String userName);

	public <S extends User> boolean save(S entity); 
	
	Optional<User> loadUserByEmail(String email);
	
	public void deleteById(String userName);
	
	public List<UserDTO> findAll();

    UserDTO changePassword(String username, String oldPassword, String newPassword);
}
