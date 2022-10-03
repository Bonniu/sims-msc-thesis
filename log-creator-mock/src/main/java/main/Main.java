package main;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Slf4j
public class Main {


    public static void main(String[] args) throws InterruptedException {
        log.info("hello wk");
        int io = 0;
        while (io < 3185825) {
            io++;
            Thread.sleep(10000);
            log.info("hello wk" + io);
            Exception ex = new Exception();
            try {
               new File("akjsnbdjasd.gowno");
            } catch (Exception e) {
                log.info("erorr", e);
                System.out.println();
            }
            log.error(String.valueOf(ex), ex);
        }

        log.info("hello end");
    }
}
