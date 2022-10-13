package main;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        log.info("hello wk");
        int io = 0;
        while (io < 3185825) {
            io++;
            Thread.sleep(1000);
            log.info("hello wk" + io);
            Exception ex = new Exception();
            Double d = null;
            var v = (int) (Math.random() * 10000);
            if (v % 3 == 0) {
                try {
                    double v1 = d.doubleValue();
                }
                catch (Exception e) {
                    log.error("Error while processing postgres connection: ", e);
                }
            }
            log.info("Active connections: " + String.valueOf(v));
            log.error(String.valueOf(ex), ex);
        }

        log.info("hello end");
    }
}
