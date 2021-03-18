package com.cpay.fileUpload;

import com.cpay.fileUpload.moveFiles.MoveFiles;
import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;



@EnableScheduling
@SpringBootApplication
public class CpayFileUploadApplication {





    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CpayFileUploadApplication.class, args);



    }


}