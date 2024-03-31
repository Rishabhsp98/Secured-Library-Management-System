package com.example.SecuredLibrarySystem.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // for us , for indexing and querying

    private String txnId;  // for user, kind of dummy, unique and random

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;  // every enum needs enumerated type

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    private Integer fine;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Book book;// one book can be a part of many transactions(issue and return etc)

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Student student; // one student can be a part of many transactions

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Admin admin; // one student can be a part of many transactions
}
