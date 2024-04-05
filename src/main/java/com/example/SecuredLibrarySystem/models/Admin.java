package com.example.SecuredLibrarySystem.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @CreationTimestamp
    private Date CreatedOn;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"admin"}) // in secured User , ignore the property admin to avoid infinite loop
    private SecuredUser securedUser;

    @OneToMany(mappedBy = "admin")
    @JsonIgnoreProperties({"admin"})
    private List<Transactions> transactionsList;

}
