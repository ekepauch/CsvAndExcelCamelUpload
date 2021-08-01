package com.kamel.fileupload.service.impl;

import com.kamel.fileupload.model.Transaction;
import com.kamel.fileupload.repository.TransactionRepository;
import com.kamel.fileupload.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);




  private TransactionRepository paymentRepository;
  @Autowired
  public void setPaymentRepository(TransactionRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }





  @Override
  public Page<Transaction> getPayments(Pageable pageable) {
    return null;
  }



  @Override
  public void savePayment(Transaction transaction) {

    DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    String datePart = df.format(new Date());
    transaction.setTransactionDate((datePart));
    paymentRepository.save(transaction);
    log.info(String.format(":::::payment details saved   %s", transaction));

  }

  @Override
  public void savePayments(List<Transaction> transactionList) {
    paymentRepository.saveAll(transactionList);
    log.info(String.format(":::::payment List saved   %s", transactionList));
  }





}
