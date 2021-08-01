package com.kamel.fileupload.filehelper;

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
        }else{
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








}
