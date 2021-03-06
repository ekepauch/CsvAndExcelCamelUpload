package com.kamel.fileupload.config;

import com.kamel.fileupload.model.Transaction;
import com.kamel.fileupload.model.data.TransactionCsvRecord;
import com.kamel.fileupload.service.TransactionService;
import com.kamel.fileupload.util.BeanUtil;
import com.kamel.fileupload.util.Utility;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Iterator;

@SuppressWarnings("ALL")
@Component
@Slf4j
public class TransactionProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    File file = exchange.getIn().getBody(File.class);
    processFile(file);
  }

  private void processFile(File file) {
      String batchId= Utility.batchIds();
    try (Reader reader = Files.newBufferedReader(file.toPath()); ) {

      CsvToBean<TransactionCsvRecord> csvToBean =
          new CsvToBeanBuilder(reader)
              .withType(TransactionCsvRecord.class)
              .withIgnoreLeadingWhiteSpace(true)
              .withSeparator(',')
              .withIgnoreQuotations(true)
              .build();

      Iterator<TransactionCsvRecord> csvUserIterator = csvToBean.iterator();

      while (csvUserIterator.hasNext()) {
        TransactionCsvRecord csvPayment = csvUserIterator.next();

          log.info(String.format("======================= details==================== "));

          log.info(String.format("" + csvPayment));

        Transaction payment = new Transaction();
        BeanUtils.copyProperties(csvPayment, payment);
        payment.setBatchId(payment.getCorporateId()+batchId);

        TransactionService paymentService = BeanUtil.getBean(TransactionService.class);
        paymentService.savePayment(payment);
          log.info(String.format("========================ends====================== "));
      }
    } catch (Exception e) {
      log.error("exception occurred while reading  file " + e.getLocalizedMessage(), e);
    }
  }
}
