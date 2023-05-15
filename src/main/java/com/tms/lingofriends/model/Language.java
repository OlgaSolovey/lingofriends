package com.tms.lingofriends.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language_table")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_seq_gen")
    @SequenceGenerator(name = "language_id_seq_gen", sequenceName = "language_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "education_product_id")
    private int educationProductId;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}