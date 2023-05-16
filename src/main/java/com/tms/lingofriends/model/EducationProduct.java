package com.tms.lingofriends.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education_product_table")
@ToString(exclude = {"user", "images","language"})
@EqualsAndHashCode(exclude = {"user ", "images","language"})
public class EducationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_product_id_seq_gen")
    @SequenceGenerator(name = "education_product_id_seq_gen", sequenceName = "education_product_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "ed_product_name")
    private String edProductName;
    @Column(name = "description")
    private String description;
    //, columnDefinition = "text"
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;
    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "educationProduct")
    private List<Image> images = new ArrayList<>();
    @Column(name = "preview_image_id")
    private Long previewImageId;
    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image) {
        image.setEducationProduct(this);
        images.add(image);
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

}