package com.tms.lingofriends.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson_table")

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_id_seq_gen")
    @SequenceGenerator(name = "lesson_id_seq_gen", sequenceName = "lesson_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "theory")
    private String theory;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "changed")
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}