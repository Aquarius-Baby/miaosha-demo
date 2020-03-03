package com.demo.miaoshademo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class MiaoshademoApplicationTests {

    @Test
    void contextLoads() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(1);
        atomicInteger.getAndIncrement();
    }

}
