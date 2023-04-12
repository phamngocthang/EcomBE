package com.ecomerce.android.sendmail;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.ecomerce.android.dto.EmailDTO;
import com.ecomerce.android.dto.ResponseDTO;
import com.ecomerce.android.model.User;
import com.ecomerce.android.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OtpService {
    private OtpGenerator otpGenerator;
    private EmailService emailService;
    private UserService userService;

    /**
     * Constructor dependency injector
     * @param otpGenerator - otpGenerator dependency
     * @param emailService - email service dependency
     * @param userService - user service dependency
     */
    public OtpService(OtpGenerator otpGenerator, EmailService emailService, UserService userService)
    {
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.userService = userService;
    }

    /**
     * Method for generate OTP number
     *
     * @param key - provided key (username in this case)
     * @return boolean value (true|false)
     */
	public Boolean generateOtp(String key, ResponseDTO responseDTO) {
		try {
			EmailDTO  emailDTO = new EmailDTO();
			
			// generate otp
			Integer otpValue = otpGenerator.generateOTP(key);
			if (otpValue == -1) {
				return false;
			}
				
			// fetch user e-mail from database
			Optional<User> user = userService.loadUserByEmail(key);
			String userEmail = key;
			if (user.isPresent()) {
				responseDTO.setMessage("The email already exists!!!");
				System.out.print("ok ok");
				return false;
			}
			List<String> recipients = new ArrayList<>();
			recipients.add(userEmail);

			// generate emailDTO object
			emailDTO.setSubject("Spring Boot OTP Password.");
			emailDTO.setBody("OTP Password: " + otpValue);
			emailDTO.setRecipients(recipients);

			// send generated e-mail
			return emailService.sendSimpleMessage(emailDTO, responseDTO);
		} catch (Exception e) {
			System.out.println("An error occurred while generating and sending OTP: " + e.getMessage());
			responseDTO.setMessage(e.getMessage());
			return false;
		}
	}

    /**
     * Method for validating provided OTP
     *
     * @param key - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
	public Boolean validateOTP(String key, Integer otpNumber, ResponseDTO responseDTO) {
		try {
			// get OTP from cache
			Integer cacheOTP = otpGenerator.getOPTByKey(key);
			if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
				otpGenerator.clearOTPFromCache(key);
				responseDTO.setMessage("GET OTP Success!!!");
				return true;
			}
			return false;
		} catch (Exception e) {
			responseDTO.setMessage("An error occurred while validating OTP: " + e.getMessage());
			System.out.println("An error occurred while validating OTP: " + e.getMessage());
			return false;
		}
	}
}