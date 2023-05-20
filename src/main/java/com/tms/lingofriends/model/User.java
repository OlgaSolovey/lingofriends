package com.tms.lingofriends.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
@ToString(exclude = {"subscription"})
@EqualsAndHashCode(exclude = {"subscription"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "email")
    private String email;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "changed")
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;


    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;
}