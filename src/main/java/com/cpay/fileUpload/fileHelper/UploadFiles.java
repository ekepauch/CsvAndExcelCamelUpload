package com.cpay.fileUpload.fileHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class UploadFiles {



    @Value("${uploads.dir}")
    String externalResourceLocation;

    public void createDirectory(String fileName) {
        File file = new File(fileName);
        //Creating the directory
        boolean bool = file.mkdir();
        if(bool){
            System.out.println("Directory created successfully");
        }else{
            System.err.println("Sorry couldn’t create specified directory");

        }

    }







    public void uploadCSV(MultipartFile file) {

        FileInputStream inputStream = null;
        BufferedOutputStream buffStream = null;
        String customizeFileName = "";
        String fullDate;
        String fileName;


        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        try {
            fileName = file.getOriginalFilename();
            createDirectory(Paths.get(externalResourceLocation).toAbsolutePath().toString());
            fullDate = ft.format(dNow);
            customizeFileName = Paths.get(externalResourceLocation + "/" + fullDate + "_" + fileName).toAbsolutePath().toString();
            byte[] bytes = file.getBytes();
            File convertFile = new File(customizeFileName);
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }


//    public void uploadCSV(MultipartFile file,String uploadName) {
//
//        FileInputStream inputStream = null;
//        BufferedOutputStream buffStream = null;
//        String customizeFileName = "";
//        String fullDate;
//        String fileName;
//
//
//        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//       // FileUploadResponse fileUploadResponse;
//        try {
//            fileName = file.getOriginalFilename();
//            createDirectory(Paths.get(externalResourceLocation).toAbsolutePath().toString());
//            fullDate = ft.format(dNow);
//          //  customizeFileName = Paths.get(externalResourceLocation + "/" + fullDate + "_" + fileName).toAbsolutePath().toString();
//          // customizeFileName = Paths.get(externalResourceLocation + "/" + uploadName).toAbsolutePath().toString();
//           customizeFileName = uploadName;
//            byte[] bytes = file.getBytes();
//            File convertFile = new File(customizeFileName);
//            convertFile.createNewFile();
//            FileOutputStream fout = new FileOutputStream(convertFile);
//            fout.write(file.getBytes());
//            fout.close();
//            //fileUploadResponse = new FileUploadResponse<>();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException("fail to store csv data: " + e.getMessage());
//        }
//       // return fileUploadResponse;
//    }





}
