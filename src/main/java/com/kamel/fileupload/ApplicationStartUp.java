package com.kamel.fileupload;

import com.kamel.fileupload.movefiles.MoveFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationStartUp implements ApplicationRunner {

    @Value("${source.dir}")
    private File sourceFile;


    @Override
    public void run (ApplicationArguments args){
        log.info("Directory clean up at : {}" , new Date());
        MoveFiles.deleteFromSource(sourceFile);
    }
}
