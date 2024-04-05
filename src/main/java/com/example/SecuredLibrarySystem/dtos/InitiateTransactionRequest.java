package com.example.SecuredLibrarySystem.dtos;


import com.example.SecuredLibrarySystem.models.TransactionType;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateTransactionRequest {

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer studentId;

    @NotNull
    private TransactionType transactionType;
}
