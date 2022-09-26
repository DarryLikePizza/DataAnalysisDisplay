package com.example.bgpm;

import org.codehaus.jettison.json.JSONException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
public class BgpmApplication {


    public static void main(String[] args) throws JSONException, IOException {
        SpringApplication.run(BgpmApplication.class, args);


    }

}
