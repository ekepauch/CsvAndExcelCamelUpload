package com.cpay.fileUpload.model;




import lombok.Data;
import javax.persistence.*;



@Entity
@Table(name ="Transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String corporateId;

    private String accountName;

    private String email;

    private String phoneNumber;

    private String category;

    private String departmentCode;

    private String accountNumber;

    private String amount;

    private String batchId;


    @Transient
    private String transactionCount;

    private String transactionDate;







}
