package com.ecomerce.android.model;

import java.io.IOException;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@Column(name="user_name")
	private String userName;
	private String address;

	private String avatar;

	private String fullname;

	private String phonenumber;

	private String province;

	private String district;

	private String subdistrict;

	@OneToOne()
	@JoinColumn(name="userName")
	private User user;

	@OneToMany(mappedBy="customer")
	private List<Order> orders;


	@OneToMany(mappedBy="customer")
	private List<Review> reviews;
}