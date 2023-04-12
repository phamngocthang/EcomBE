package com.ecomerce.android.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.ecomerce.android.dto.ImageDTO;
import com.ecomerce.android.dto.UserDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.User;
import com.ecomerce.android.responsitory.UserRepository;
import com.ecomerce.android.service.UserService;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	Mapper mapper;

	public <S extends User> boolean save(S entity) {
		return userRepository.save(entity) != null;
	}

	public UserDTO findById(String userName) {
		Optional<User> user = userRepository.findById(userName);
		if(user.isPresent())
			return mapper.convertTo(user, UserDTO.class);
		else {
			return null;
		}
	}

	public boolean checkLogin(User user) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		List<User> listUser = userRepository.findAll();
		for (User userExist : listUser) {
			if (StringUtils.equals(user.getEmail(), userExist.getEmail())
					&& passwordEncoder.matches(user.getPassword(), userExist.getPassword())) {
				return true;
			}
		}
		return false;
	}

	public void delete(User entity) {
		userRepository.delete(entity);
	}
	
	public Optional<User> loadUserByEmail(String email) { 
//		GrantedAuthority authority = new SimpleGrantedAuthority(userPojo.getRole());
//		UserDetails userDetails = (UserDetails) new User(userPojo.getUsername(),
//		userPojo.getPassword(), Arrays.asList(authority));
		return userRepository.getByEmail(email);
	}

	public void deleteById(String userName) {
		userRepository.deleteById(userName);
	}

	public List<UserDTO> findAll() {
		return userRepository.findAll()
				.stream()
				.map(user -> mapper.convertTo(user, UserDTO.class))
				.collect(Collectors.toList());
	}

}