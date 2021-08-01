package com.kamel.fileupload.service;

import com.kamel.fileupload.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {



  Page<Transaction> getPayments(Pageable pageable);


  void savePayment(Transaction transaction);


  void savePayments(List<Transaction> transactionList);


}
