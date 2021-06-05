package com.graduate.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }

}
