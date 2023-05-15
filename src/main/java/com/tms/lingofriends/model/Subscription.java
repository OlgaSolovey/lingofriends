package com.tms.lingofriends.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_table")
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {" user"})

public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq_gen")
    @SequenceGenerator(name = "subscription_id_seq_gen", sequenceName = "subscription_table_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp expireDate;
   @JsonBackReference
    @OneToOne(mappedBy = "subscription")
    private User user;
}