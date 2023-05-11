package com.tms.lingofriends.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_is_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
   // @Enumerated(EnumType.ORDINAL)
    private String role;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone_number")
    private String telephoneNumber;
    @Column(name = "birthday_day")
    @Temporal(TemporalType.DATE)
    private Date birthdayDay;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;
    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean idDeleted;
}