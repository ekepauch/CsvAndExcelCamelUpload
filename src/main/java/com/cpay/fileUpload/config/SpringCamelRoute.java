package com.cpay.fileUpload.config;



import com.cpay.fileUpload.excelhandler.ExcelTransactionProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SpringCamelRoute extends RouteBuilder {



  private static final String QUESTION_MARK = "?";
  private static final String AMPERSAND = "&";
  private static final String COLON = ":";

  @Value("${camel.batch.timeout}")
  private long batchTimeout;

  @Value("${camel.batch.max.records}")
  private int maxRecords;

  @Value("${source.type}")
  private String sourceType;

  @Value("${source.location}")
  private String sourceLocation;

  @Value("${dest.location}")
  private String destLocation;

  @Value("${noop.flag}")
  private boolean isNoop;

  @Value("${recursive.flag}")
  private boolean isRecursive;

  @Value("${file.type}")
  private String fileType;

  @Value("${xlsxfile.type}")
  private String xlsxfileType;




  @Autowired
  private ExcelTransactionProcessor excelConverterBean;

  @Override
  public void configure() {



    from(buildSourceFileUrl())
        .log(String.format("received file on %s", new Date()))
            .split(body())
            .streaming()
       .process(new TransactionProcessor())
            .log(String.format("completed file upload on %s", new Date()))
        .to(buildDestFileUrl())
            .end();


    from(buildSourcexlsxFileUrl())
            .log(String.format("received excel file on %s", new Date()))
            .split(body())
            .streaming()
              .bean(excelConverterBean,"processExcelData")
            .log(String.format("completed excel file upload on %s", new Date()))
            .to(buildDestFileUrl())
            .end();
  }



  private String buildSourceFileUrl() {
    return sourceType
        + COLON
        + sourceLocation
        + QUESTION_MARK
        + "noop="
        + isNoop
        + AMPERSAND
        + "recursive="
        + isRecursive
        + AMPERSAND
        + "include="
        + fileType;
  }

  private String buildDestFileUrl() {
    return sourceType + COLON + destLocation;
  }


  private String buildSourcexlsxFileUrl() {
    return sourceType
            + COLON
            + sourceLocation
            + QUESTION_MARK
            + "noop="
            + isNoop
            + AMPERSAND
            + "recursive="
            + isRecursive
            + AMPERSAND
            + "include="
            + xlsxfileType;
  }

}



