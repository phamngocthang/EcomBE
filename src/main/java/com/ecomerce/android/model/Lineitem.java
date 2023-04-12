package com.ecomerce.android.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Lineitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "lineitem_ID")
	private Integer lineItemId;

	@Column(name="created_at")
	private Timestamp createdAt;

	private int quantity;

	@Column(name="update_at")
	private Timestamp updateAt;

	//bi-directional many-to-one association to Order
	@ManyToOne()
	@JoinColumn(name="orderId")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne()
	@JoinColumn(name = "options_ID")
	private Option option;
}