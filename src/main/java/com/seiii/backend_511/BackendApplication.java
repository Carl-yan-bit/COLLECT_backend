package com.seiii.backend_511;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@SpringBootApplication

public class BackendApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        SpringApplication.run(BackendApplication.class, args);
        String LOCATION = URLDecoder.decode(BackendApplication.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
                "UTF-8");
        System.out.println("获取路径成功：LOCATION=" + LOCATION);

    }

}
