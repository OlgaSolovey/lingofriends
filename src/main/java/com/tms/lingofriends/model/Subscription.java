package com.tms.lingofriends.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp expireDate;
    @Column(name = "status")
    private boolean status;
}