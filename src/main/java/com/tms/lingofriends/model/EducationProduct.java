package com.tms.lingofriends.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education_product_table")
public class EducationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_product_id_seq_gen")
    @SequenceGenerator(name = "education_product_id_seq_gen", sequenceName = "education_product_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "ed_product_name")
    private String edProductName;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;
    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "educationProduct")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }
    public void addImageToProduct(Image image){
        image.setEducationProduct(this);
        images.add(image);
    }
}