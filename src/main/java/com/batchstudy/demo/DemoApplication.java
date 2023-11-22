package com.batchstudy.demo;


import com.batchstudy.demo.repository.pass.PassEntity;
import com.batchstudy.demo.repository.pass.PassRepository;
import com.batchstudy.demo.repository.pass.PassStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Example;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@EnableBatchProcessing // Batch 허용
@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

}
