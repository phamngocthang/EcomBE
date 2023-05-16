package com.ecomerce.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.ResponseObject;
import com.ecomerce.android.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.android.dto.EmailDTO;
import com.ecomerce.android.dto.ResponseDTO;
import com.ecomerce.android.jwt.service.JwtService;
import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.User;
import com.ecomerce.android.sendmail.OtpService;
import com.ecomerce.android.service.CustomerService;
import com.ecomerce.android.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private JwtService jwtService;

	@Autowired(required = false)
	private UserService userService;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IStorageService storageService;
	
	ResponseDTO responseDTO = new ResponseDTO();


	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}


	/* ---------------- GET USER BY ID ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable("id") String userName) {
		UserDTO user = userService.findById(userName);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	User User;
	
	@RequestMapping(value = "/SignUp", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> sendMail(@RequestBody User user) {	
		
		if(otpService.generateOtp(user.getEmail(), responseDTO)) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			User = user;
			System.out.print(user.getEmail());
			System.out.println(User.getUserName() + " " + User.getEmail());
			responseDTO.setHttpcode(HttpStatus.CREATED);
		} else {
			responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(responseDTO.getHttpcode()).body(responseDTO);
	}
	
	@RequestMapping(value = "/SignUp/Verify", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> createUser(@RequestParam Integer otp, @RequestParam String email) {
		if(otpService.validateOTP(email, otp, responseDTO)) {
			Customer customer = new Customer(User.getUsername(),1,1,1);
			if(userService.save(User) && customerService.save(customer)) {
				responseDTO.setMessage("Create User Success!!");
				responseDTO.setHttpcode(HttpStatus.CREATED);
			}else {
				responseDTO.setMessage("Wrong When Save User!!");
				responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
			}
		} else {
			
			responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
		}	
		return ResponseEntity.status(responseDTO.getHttpcode()).body(responseDTO);
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable("id") String userName) {
		userService.deleteById(userName);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> login(HttpServletRequest request, @RequestBody User user) throws IOException {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("");
		responseDTO.setHttpcode(null);

		try {
			if (userService.checkLogin(user)) {
				Optional<com.ecomerce.android.model.User> checkuser = userService.loadUserByEmail(user.getEmail());
				responseDTO.setMessage(jwtService.generateTokenLogin(user.getEmail(),checkuser.get().getRole()));
				responseDTO.setHttpcode(HttpStatus.OK);
			} else {
				responseDTO.setMessage("Wrong userId or password");
				responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			responseDTO.setMessage("Server Error");
			responseDTO.setHttpcode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(responseDTO.getHttpcode()).body(responseDTO);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(HttpServletRequest request, @RequestBody User user){
		return null;
       
    }
	@RequestMapping(value="/getUserName", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getUserName(HttpServletRequest request, @RequestParam("email") String email){
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("");
		responseDTO.setHttpcode(null);

		try {
			if (userService.getUsernameByEmail(email) != null) {
				responseDTO.setMessage(userService.getUsernameByEmail(email));
				responseDTO.setHttpcode(HttpStatus.OK);
			} else {
				responseDTO.setMessage("Not Found User Name");
				responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			responseDTO.setMessage("Server Error");
			responseDTO.setHttpcode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(responseDTO.getHttpcode()).body(responseDTO);
       
    }
	@RequestMapping(value="/SignUp/check-signup", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAllUserName(HttpServletRequest request,
    		@RequestParam("username") String username,
    		@RequestParam("email") String email){
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("");
		responseDTO.setHttpcode(null);

		try {
			List<String> listUsername = userService.getAllUsername();
			List<String> listEmail = userService.getAllEmail();
			
			if (listUsername.contains(username) || listEmail.contains(email) ) {
				responseDTO.setMessage("Check User Sign Up Fail");
				responseDTO.setHttpcode(HttpStatus.BAD_REQUEST);
			} else {
				responseDTO.setMessage("Check User Sign Up Success");
				responseDTO.setHttpcode(HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			responseDTO.setMessage("Server Error");
			responseDTO.setHttpcode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(responseDTO.getHttpcode()).body(responseDTO);
       
    }

	@GetMapping(value = "/change-password")
	public ResponseEntity<?> updatePassword(@RequestParam("username") String username,
											@RequestParam("oldPassword") String oldPassword,
											@RequestParam("newPassword") String newPassword) {
		UserDTO userDTO = userService.changePassword(username, oldPassword, newPassword);
		if(userDTO.getUserName() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Success", "Update Password Successfully", userDTO)
			);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("Failed", "Error", "")
			);
		}
	}

}
