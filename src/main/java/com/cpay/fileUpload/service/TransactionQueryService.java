package com.cpay.fileUpload.service;

import com.cpay.fileUpload.model.Transaction;
import com.cpay.fileUpload.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionQueryService {


    @Autowired
    TransactionRepository transactionRepository;


    public Page<Transaction> getBatch(PageRequest pageRequest) {
        return transactionRepository.transactionsByGrouping(pageRequest);
    }


    public Page<Transaction> getCorporateBatch(String corporateId,PageRequest pageRequest) {
        return transactionRepository.corporateTransactionsByGrouping(corporateId,pageRequest);
    }

    public Page<Transaction> getTransactions(String batchId,PageRequest pageRequest) {
        return transactionRepository.getTransactions(batchId,pageRequest);
    }

    public Page<Transaction> getCorporateTransactions(String batchId,String corporateId,PageRequest pageRequest) {
        return transactionRepository.getCorporateTransactions(batchId,corporateId,pageRequest);
    }
}
