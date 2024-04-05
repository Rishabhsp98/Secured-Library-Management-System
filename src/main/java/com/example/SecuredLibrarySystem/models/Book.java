package com.example.SecuredLibrarySystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // giving the Database the power not to hibernate
    private Integer id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    // by default its ordinal(means numbered), we are using string based for better readability
    private Genre genre; //   Either Store as String or ENUM

    @CreationTimestamp
    private Date createOn;

    @UpdateTimestamp
    private Date updateOn;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})   // to avoid infinite loop
    private Author my_author;             // my_author was again calling book, which was again calling my_author

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})   // to avoid infinite loop
    private Student student;              // student was again calling book, which was again calling student

    @OneToMany(mappedBy = "book")
    private List<Transactions> transactionList;
}


