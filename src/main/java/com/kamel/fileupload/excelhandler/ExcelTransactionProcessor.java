package com.kamel.fileupload.excelhandler;

import com.kamel.fileupload.model.Transaction;
import com.kamel.fileupload.service.TransactionService;
import com.kamel.fileupload.util.BeanUtil;
import com.kamel.fileupload.util.RowError;
import com.kamel.fileupload.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.util.NumberToTextConverter.toText;

@Component("excelTransactionProcessor")
@Slf4j
public class ExcelTransactionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ExcelTransactionProcessor.class);






    private static Workbook getWorkbook(File file) throws IOException {
        Workbook workbook = null;
           workbook = new XSSFWorkbook(file.getPath());
        return workbook;
    }





    private static Object getCellValue(Cell cell) {

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return toText(cell.getNumericCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
        }
        return null;
    }


    private Transaction transactionObjectResolver(Row row, String batchId) {

        Transaction data = new Transaction();
        try {
            data.setCorporateId(getCellValue(row.getCell(0)).toString());
            data.setAccountName(getCellValue(row.getCell(1)).toString());
            data.setEmail(getCellValue(row.getCell(2)).toString());
            data.setPhoneNumber(getCellValue(row.getCell(3)).toString());
            data.setCategory(getCellValue(row.getCell(4)).toString());
            data.setDepartmentCode(getCellValue(row.getCell(5)).toString());
            data.setAccountNumber(getCellValue(row.getCell(6)).toString());
            data.setAmount(getCellValue(row.getCell(7)).toString());

            data.setBatchId(getCellValue(row.getCell(0)).toString()+batchId);
            TransactionService paymentService = BeanUtil.getBean(TransactionService.class);
            paymentService.savePayment(data);
        }catch (Exception e){
             logger.info(":::::: Error in transaction Upload row ::::::");
        }

        return data;
    }





    public void processExcelData(@Body File file) throws IOException {

        String batchId= Utility.batchIds();
     try{
        Workbook workbook;
        DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

        List<Transaction> transactionList = new ArrayList<>();
        workbook = getWorkbook(file);
        Sheet firstSheet = workbook.getSheetAt(0);//GET FIRST SHEET
        Iterator<Row> rowIterator = firstSheet.iterator();

        while (rowIterator.hasNext()) {
            boolean errorFound = false;
            String cellErrorMsg = "";

            Row nextRow = rowIterator.next();
            if (nextRow.getRowNum() == 0) {//SKIP HEADERS(FIRST ROW)
                continue;
            }
            RowError error = new RowError();
            int lastCellNum = nextRow.getLastCellNum();
            for (int cn = 0; cn <= lastCellNum; cn++) {
                Cell c = nextRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
            }
            Transaction transaction = transactionObjectResolver(nextRow,batchId);
            transactionList.add(transaction);
        }
     } catch (Exception e) {
         log.error("exception occurred while reading  file " + e.getLocalizedMessage(), e);
     }

    }







}