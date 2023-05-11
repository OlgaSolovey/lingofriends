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
@Table(name="education_product_table")
public class EducationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_product_id_seq_gen")
    @SequenceGenerator(name = "education_product_id_seq_gen", sequenceName = "education_product_table_is_seq", allocationSize = 1)
    private Integer id;
    @Column(name="ed_product_name")
    private String edProductName;
    @Column(name="user_id")
    private int userId;
    @Column(name="language_id")
    private String languageId;
    @Column(name="created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;
    @Column(name="changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;
    @Column(name="is_deleted")
    private boolean isDeleted;
}
