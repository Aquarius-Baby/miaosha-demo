package com.demo.miaoshademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;


@SpringBootApplication
@EnableAsync
public class MiaoshademoApplication {

    public static void main(String[] args) {
        Arrays.sort(new int[3]);
        SpringApplication.run(MiaoshademoApplication.class, args);
    }
}
