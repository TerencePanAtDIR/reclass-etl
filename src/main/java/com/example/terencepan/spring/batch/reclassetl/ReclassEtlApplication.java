package com.example.terencepan.spring.batch.reclassetl;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@EnableBatchProcessing
@SpringBootApplication
public class ReclassEtlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReclassEtlApplication.class, args);
	}

}
