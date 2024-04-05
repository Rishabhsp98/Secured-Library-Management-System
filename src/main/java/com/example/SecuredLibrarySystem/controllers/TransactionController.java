package com.example.SecuredLibrarySystem.controllers;


import com.example.SecuredLibrarySystem.dtos.InitiateTransactionRequest;
import com.example.SecuredLibrarySystem.dtos.MakePaymentRequest;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction")
    public String initiateTxn(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser =(SecuredUser) authentication.getPrincipal();
        Integer AdminId = securedUser.getAdmin().getId();
        return transactionService.InitiateTxn(initiateTransactionRequest,AdminId);
    }
    @PostMapping("/transaction/payment")
    public void makePayment(@RequestBody @Valid MakePaymentRequest makePaymentRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser =(SecuredUser) authentication.getPrincipal();
        Integer studentId = securedUser.getStudent().getId();
        transactionService.PayFine(makePaymentRequest,studentId);
    }

//    @PostMapping("/transaction/payment")
//    public void makePayment(@RequestParam("amount") Integer amount,
//                            @RequestParam("studentId") Integer studentId,
//                            @RequestParam("transactionId") String txnId) throws Exception {
//        transactionService.payFine(amount, studentId, txnId);
//    }
}
