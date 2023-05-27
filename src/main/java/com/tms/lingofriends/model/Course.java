package com.tms.lingofriends.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_table")
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_name_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "changed")
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}