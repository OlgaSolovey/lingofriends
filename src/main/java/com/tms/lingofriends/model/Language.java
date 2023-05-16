package com.tms.lingofriends.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language_table")
@ToString(exclude = { "educationProductsList","userList"})
@EqualsAndHashCode(exclude = {"educationProductsList","userList"})
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_seq_gen")
    @SequenceGenerator(name = "language_id_seq_gen", sequenceName = "language_table_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "language_name")
    private String languageName;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @JsonManagedReference
    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private Set<User> userList = new HashSet<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private Set<EducationProduct> educationProductsList = new HashSet<>();
}