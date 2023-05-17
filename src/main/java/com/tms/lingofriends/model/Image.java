package com.tms.lingofriends.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images_table")
@ToString(exclude = {"educationProduct"})
@EqualsAndHashCode(exclude = {"educationProduct"})
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_id_seq_gen")
    @SequenceGenerator(name = "images_id_seq_gen", sequenceName = "images_table_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "is_preview_image")
    private boolean isPreviewImage;


    @Lob
    private byte[] bytes;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "education_product_id", referencedColumnName = "id")
    private EducationProduct educationProduct;
}