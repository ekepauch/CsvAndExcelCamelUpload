package com.cpay.fileUpload.model.data;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@ToString
public class TransactionCsvRecord {

  @CsvBindByName(column = "corporateId")
  private String corporateId;

  @CsvBindByName(column = "accountName")
  private String accountName;

  @CsvBindByName(column = "email")
  private String email;

  @CsvBindByName(column = "phoneNumber")
  private String phoneNumber;

  @CsvBindByName(column = "category")
  private String category;

  @CsvBindByName(column = "departmentCode")
  private String departmentCode;

  @CsvBindByName(column = "accountNumber")
  private String accountNumber;

  @CsvBindByName(column = "amount")
  private String amount;

//  @CsvBindByName(column = "batchId")
//  private String batchId;


}
