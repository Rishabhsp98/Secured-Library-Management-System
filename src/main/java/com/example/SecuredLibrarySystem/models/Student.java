package com.example.SecuredLibrarySystem.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private Integer age;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"student"}) // in secured User , ignore the property student to avoid infinite loop
    private SecuredUser securedUser;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Book> bookList;  // back reference

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Transactions> transactionList;  // back reference
}
