package com.lele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  @SpringBootApplication 来标注一个主程序类
 */
@SpringBootApplication
public class HelloWorldMainApplication {
    public static void main(String args[])
    {
        SpringApplication.run(HelloWorldMainApplication.class,args);
    }
}

