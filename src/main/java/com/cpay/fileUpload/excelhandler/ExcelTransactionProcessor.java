package com.cpay.fileUpload.excelhandler;

import com.cpay.fileUpload.model.Transaction;
import com.cpay.fileUpload.service.TransactionService;
import com.cpay.fileUpload.util.BeanUtil;
import com.cpay.fileUpload.util.RowError;
import com.cpay.fileUpload.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.util.NumberToTextConverter.toText;

@Component("excelTransactionProcessor")
@Slf4j
public class ExcelTransactionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ExcelTransactionProcessor.class);




//    private static Workbook getWorkbook(InputStream inputStream) throws IOException {
//        Workbook workbook = null;
////        if (excelFilePath.endsWith("xlsx")) {
//        workbook = new XSSFWorkbook(inputStream);
////        } else if (excelFilePath.endsWith("xls")) {
////            workbook = new HSSFWorkbook(inputStream);
////        } else {
////          throw new ConflictException(CustomResponseCode.FORMAT_NOT_ALLOWED, " wrong file format");
////        }
//        return workbook;
//    }


    private static Workbook getWorkbook(File file) throws IOException {
        Workbook workbook = null;
//        if (excelFilePath.endsWith("xlsx")) {
           workbook = new XSSFWorkbook(file.getPath());
//        } else if (excelFilePath.endsWith("xls")) {
//            workbook = new HSSFWorkbook(inputStream);
//        } else {
//          throw new ConflictException(CustomResponseCode.FORMAT_NOT_ALLOWED, " wrong file format");
//        }
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


    private Transaction transactionObjectResolver(Row row,String batchId) {

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
            paymentService.saveExcelPayment(data);
        }catch (Exception e){
             logger.info(":::::: Error in transaction Upload row ::::::");
        }

        return data;
    }




//    private String validateTransactionUploadCell(Cell cell, int columnIndex) {
//
//        String errorMsg = null;
//        if (columnIndex == 0 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "CORPORATE ID field can not be validated";
//        }
//        if (columnIndex == 1 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "ACCOUNT NAME field can not be validated";
//        }
//        if (columnIndex == 2 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ||
//                (!Utility.validEmail(getCellValue(cell).toString())))) {
//            errorMsg = " EMAIL can not be validated";
//        }
//        if (columnIndex == 3 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ||
//                !Utility.isNumeric((getCellValue(cell).toString())) || getCellValue(cell).toString().length() < 10)) {
//            errorMsg = " PHONE NUMBER can not be validated";
//        }
//
//        if (columnIndex == 4 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "CATEGORY  field can not be validated";
//        }
//
//        if (columnIndex == 5 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "DEPARTMENT CODE field can not be validated";
//        }
//
//        if (columnIndex == 6 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "ACCOUNT NUMBER field can not be validated";
//        }
//
//        if (columnIndex == 7 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
//            errorMsg = "AMOUNT field can not be validated";
//        }
//        return errorMsg;
//    }


    public void processExcelData(@Body File file, Exchange ex) throws IOException {


        System.out.println(":::::::::  excel file ::::::::::" + file);
        String batchId=Utility.batchIds();
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
               // cellErrorMsg = validateTransactionUploadCell(c, cn);
            }
            Transaction transaction = transactionObjectResolver(nextRow,batchId);
            transactionList.add(transaction);
        }
     } catch (Exception e) {
         log.error("exception occurred while reading  file " + e.getLocalizedMessage(), e);
     }

    }





//
//    public void processExcelData(@Body InputStream inputStream, Exchange ex) throws IOException {
//        //System.out.println(":::::::::  excel file ::::::::::" + inputStream);
//        String batchId=Utility.batchIds();
//        try{
//            Workbook workbook;
//            DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
//
//            List<Transaction> transactionList = new ArrayList<>();
//            workbook = getWorkbook(inputStream);
//            Sheet firstSheet = workbook.getSheetAt(0);//GET FIRST SHEET
//            Iterator<Row> rowIterator = firstSheet.iterator();
//
//            while (rowIterator.hasNext()) {
//                boolean errorFound = false;
//                String cellErrorMsg = "";
//
//                Row nextRow = rowIterator.next();
//                if (nextRow.getRowNum() == 0) {//SKIP HEADERS(FIRST ROW)
//                    continue;
//                }
//                RowError error = new RowError();
//                int lastCellNum = nextRow.getLastCellNum();
//                for (int cn = 0; cn <= lastCellNum; cn++) {
//                    Cell c = nextRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
//                    // cellErrorMsg = validateTransactionUploadCell(c, cn);
//                }
//                Transaction transaction = transactionObjectResolver(nextRow,batchId);
//                transactionList.add(transaction);
//            }
//        } catch (Exception e) {
//            log.error("exception occurred while reading  file " + e.getLocalizedMessage(), e);
//        }
//        IOUtils.closeQuietly(inputStream);
//    }



}