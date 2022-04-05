package com.paas.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Date 2022/4/4 周一 17:48
 * @Author xu
 * @FileName PaasEurekaApplication
 * @Description eureka注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class PaasEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasEurekaApplication.class,args);
    }
}
