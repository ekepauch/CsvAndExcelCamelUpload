package com.cpay.fileUpload.service.impl;

import com.cpay.fileUpload.model.Transaction;
import com.cpay.fileUpload.repository.TransactionRepository;
import com.cpay.fileUpload.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.substring;

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

//  @Override
//  public void savePayment(Transaction transaction,String fileName) {
//
//    DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//    String datePart = df.format(new Date());
//    Batch batch = batchRepository.findByCorporateIdAndFileName(transaction.getCorporateId(),fileName);
//    transaction.setBatchId(batch.getBatchId());
//    transaction.setTransactionDate((datePart));
//    paymentRepository.save(transaction);
//    log.info(String.format(":::::payment details saved   %s", transaction));
//
//  }

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



  @Override
  public void saveExcelPayment(Transaction transaction) {

    DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    String datePart = df.format(new Date());

    transaction.setTransactionDate((datePart));
    paymentRepository.save(transaction);
    log.info(String.format(":::::payment details saved   %s", transaction));

  }

}
