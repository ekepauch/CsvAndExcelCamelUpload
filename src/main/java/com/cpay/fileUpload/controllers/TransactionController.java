package com.cpay.fileUpload.controllers;





import com.cpay.fileUpload.exceptions.ConflictException;

import com.cpay.fileUpload.fileHelper.UploadFiles;

import com.cpay.fileUpload.model.Transaction;

import com.cpay.fileUpload.service.TransactionQueryService;
import com.cpay.fileUpload.util.Constants;
import com.cpay.fileUpload.util.CustomResponseCode;
import com.cpay.fileUpload.util.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    @Value("${uploads.dir}")
    String externalResourceLocation;

    @Autowired
    private UploadFiles uploadFiles;
    @Autowired
    TransactionQueryService transactionQueryService;



//    @RequestMapping(value = "/upload", method = RequestMethod.POST,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file,
//                                        @RequestParam("corporateId") String corporateId)
//            throws IOException {
//        HttpStatus httpCode = HttpStatus.INTERNAL_SERVER_ERROR;
//        Response resp = new Response();
//        if (file.isEmpty()) {
//            throw new ConflictException(CustomResponseCode.NO_CONTENT, " failed to upload");
//        }
//        String fileName = file.getOriginalFilename();
//        if (!fileName.endsWith(Constants.CSV) && !fileName.endsWith(Constants.XLSX) && !fileName.endsWith(Constants.XLS)) {
//            throw new ConflictException(CustomResponseCode.FORMAT_NOT_ALLOWED, " wrong file format");
//        }
//        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//        String fullDate = ft.format(dNow);
//        String uploadName =  Paths.get(externalResourceLocation + "/" + fullDate + "_" + fileName ).toAbsolutePath().toString();
//
//                    uploadFiles.uploadCSV(file,uploadName);
//
//                    Batch batch = new Batch();
//                    String trans = Utility.batchId(corporateId);
//                    batch.setBatchId(trans);
//                    batch.setCorporateId(corporateId);
//                    batch.setFileName(uploadName);
//                    batchRepository.save(batch);
//
//                resp.setCode(CustomResponseCode.SUCCESS);
//                resp.setDescription("Transaction processing");
//        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
//    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file)
            throws IOException {

        HttpStatus httpCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Response resp = new Response();
        if (file.isEmpty()) {
            throw new ConflictException(CustomResponseCode.NO_CONTENT, " failed to upload");
        }
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(Constants.CSV) && !fileName.endsWith(Constants.XLSX)) {
            throw new ConflictException(CustomResponseCode.FORMAT_NOT_ALLOWED, " wrong file format");
        }
        uploadFiles.uploadCSV(file);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Transaction processing");

        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
    }




    @GetMapping("/batchTransaction")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response> batchTransaction(@RequestParam(value = "corporateId", required = false)String corporateId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        HttpStatus httpCode;
        Response resp = new Response();
        if(corporateId.isEmpty() && corporateId.equals("")) {
            Page<Transaction> transactions = transactionQueryService.getBatch(PageRequest.of(page, pageSize));
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("records retrieved");
            resp.setData(transactions);
        }else{
            Page<Transaction> transactions = transactionQueryService.getCorporateBatch(corporateId,PageRequest.of(page, pageSize));
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("records retrieved");
            resp.setData(transactions);
        }
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }




    @GetMapping("/transactions")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response> Transactions(@RequestParam(value = "batchId", required =true)String batchId,
                                                 @RequestParam(value = "corporateId", required = false)String corporateId,
                                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        HttpStatus httpCode;
        Response resp = new Response();
        if(corporateId.isEmpty() && corporateId.equals("")) {
            Page<Transaction> transactions = transactionQueryService.getTransactions(batchId, PageRequest.of(page, pageSize));
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("records retrieved");
            resp.setData(transactions);
        }else{
            Page<Transaction> corporateTransactions = transactionQueryService.getCorporateTransactions(batchId,corporateId,
                    PageRequest.of(page, pageSize));
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("records retrieved");
            resp.setData(corporateTransactions);
        }
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }





}
