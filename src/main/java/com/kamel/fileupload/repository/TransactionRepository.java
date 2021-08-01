package com.kamel.fileupload.repository;

import com.kamel.fileupload.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {





    @Query("select t.batchId,t.corporateId, count(batchId) as transactionCount from Transaction t GROUP BY t.batchId,t.corporateId ")
    Page<Transaction> transactionsByGrouping(Pageable pageable);

    @Query("select t.batchId,t.corporateId, count(batchId) as transactionCount from Transaction t where (t.corporateId = ?1) GROUP BY t.batchId,t.corporateId ")
    Page<Transaction> corporateTransactionsByGrouping(String corporateId,Pageable pageable);


    @Query("select t from Transaction t where (t.batchId = ?1)")
    Page<Transaction> getTransactions(String batchId,Pageable pageable);

    @Query("select t from Transaction t where (t.batchId = ?1) AND (t.corporateId = ?2)")
    Page<Transaction> getCorporateTransactions(String batchId,String corporateId,Pageable pageable);
}
