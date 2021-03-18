package com.cpay.fileUpload.service;

import com.cpay.fileUpload.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface TransactionService {



  Page<Transaction> getPayments(Pageable pageable);

  //void savePayment(Transaction transaction,String fileName);
  void savePayment(Transaction transaction);

  void saveExcelPayment(Transaction transaction);

  void savePayments(List<Transaction> transactionList);


}
