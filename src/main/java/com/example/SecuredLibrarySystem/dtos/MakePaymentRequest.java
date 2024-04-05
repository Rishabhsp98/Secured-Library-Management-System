package com.example.SecuredLibrarySystem.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakePaymentRequest {

    private Integer amount;

    private String transactionId;
}
