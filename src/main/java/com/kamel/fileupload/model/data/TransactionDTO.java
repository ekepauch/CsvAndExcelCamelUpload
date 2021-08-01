package com.kamel.fileupload.model.data;

import lombok.Data;

@Data
public class TransactionDTO {


  private String corporateId;
  private String accountName;
  private String email;
  private String phoneNumber;
  private String category;
  private String departmentCode;
  private String accountNumber;
  private String amount;
  private String batchId;
}
