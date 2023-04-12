package com.ecomerce.android.model;

import java.io.Serializable;
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
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_ID")
	private int productId;

	private String cpu;

	@Column(name="created_at")
	private Timestamp createdAt;

	private String description;

	private String origin;

	private String os;

	@Column(name="product_name")
	private String productName;

	private String battery;

	private String screen;

	private Double price;

	@Column(name="update_at")
	private Timestamp updateAt;

	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<Option> options;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="brandId")
	private Brand brand;

}