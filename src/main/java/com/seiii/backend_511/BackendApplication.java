package com.seiii.backend_511;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class BackendApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        SpringApplication.run(BackendApplication.class, args);
        String LOCATION = URLDecoder.decode(BackendApplication.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
                "UTF-8");
        System.out.println("获取路径成功：LOCATION=" + LOCATION);
    }

}
