package com.tms.lingofriends.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
@ToString(exclude = {"subscription", "educationProductsList"})
@EqualsAndHashCode(exclude = {"subscription", "educationProductsList"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
    @SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_table_id_seq", allocationSize = 1)
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
    private boolean isDeleted;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<EducationProduct> educationProductsList = new HashSet<>();
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
}