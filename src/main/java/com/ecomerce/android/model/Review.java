package com.ecomerce.android.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_ID")
    private Integer reviewId;

    private Integer rate;

    private String content;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="update_at")
    private Timestamp updateAt;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "userName")
    private Customer customer;

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
