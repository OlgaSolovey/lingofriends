package com.tms.lingofriends.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_table")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq_gen")
    @SequenceGenerator(name = "subscription_id_seq_gen", sequenceName = "subscription_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "expire_date")
    private LocalDate expireDate;
    @Column(name = "status")
    private Boolean status;
}