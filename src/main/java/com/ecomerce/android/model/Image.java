package com.ecomerce.android.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_ID")
	private int imageId;

	private String path;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="update_at")
	private Timestamp updateAt;

	@ManyToOne
	@JoinColumn(name = "options_ID")
	private Option option;

	@PrePersist
	public void prePersist() {
		this.createdAt = Timestamp.valueOf(LocalDateTime.now());
		this.updateAt = Timestamp.valueOf(LocalDateTime.now());
	}

	@PreUpdate
	public void preUpdate() {
		this.updateAt = Timestamp.valueOf(LocalDateTime.now());
	}
}