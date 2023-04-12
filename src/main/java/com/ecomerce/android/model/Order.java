package com.ecomerce.android.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_ID")
	private int orderId;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="order_date")
	private Timestamp orderDate;

	@Column(name="total_price")
	private double totalPrice;

	@Column(name="update_at")
	private Timestamp updateAt;


	@OneToMany(mappedBy="order")
	private List<Lineitem> lineitems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userName")
	private Customer customer;
}