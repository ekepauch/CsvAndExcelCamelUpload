package com.kamel.fileupload.movefiles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
@Slf4j
@Component
public class MoveFiles {


    @Value("${source.dir}")
    File sourceFile;

    @Value("${target.dir}")
    File targetFile;





    private static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }




     public static void  deleteFromSource(File sourceLocation) {
      File folder = new File(String.valueOf(sourceLocation));
    if (folder.exists() && folder.isDirectory()) {
        for (File f : folder.listFiles()) {
            if (f.delete()) {
                log.info(String.format("' deleted successfully %s" ,f.getName()));
            } else {
                log.info(String.format("Fail to delete '   %s", f.getName() + "'"));
            }
        }
    }
}


   // @Scheduled(cron="0 0 0 * * ?")
   @Scheduled(cron="${cronExpression}")
    public void fileJob() {
       log.info(String.format("::Cron Job Started at :   %s", new Date()));
       deleteFromSource(sourceFile);


   }
}