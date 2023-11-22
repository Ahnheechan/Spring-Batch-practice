package com.batchstudy.demo;

import com.batchstudy.demo.repository.pass.PassEntity;
import com.batchstudy.demo.repository.pass.PassRepository;
import com.batchstudy.demo.repository.pass.PassStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;




@RestController
@RequiredArgsConstructor
public class BatchController {

    private final PassRepository passRepository;
    private final JobLauncher jobLauncher;
    private final Job job;


    @GetMapping("/batch-start")
    public String startBatch() throws Exception{
        jobLauncher.run(job,new JobParameters());
        return "완료";
    }

    @GetMapping("/make-entity")
    public String makeEntity() throws Exception{
        addPassEntities(5);
        addPassEntities2(5);
        return "완료";
    }

    public void addPassEntities(int size) {
        final LocalDateTime now = LocalDateTime.now();
        final Random random = new Random();

        List<PassEntity> passEntities = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            PassEntity passEntity = new PassEntity();
            passEntity.setPackageSeq(1);
            passEntity.setUserId("A" + 1000000 + i);
            passEntity.setStatus(PassStatus.PROGRESSED);
            passEntity.setRemainingCount(random.nextInt(11));
            passEntity.setStartedAt(now.minusDays(60));
            passEntity.setEndedAt(now.minusDays(1));
            passEntities.add(passEntity);

        }
        passRepository.saveAll(passEntities);
    }

    public void addPassEntities2(int size) {
        final LocalDateTime now = LocalDateTime.now();
        final Random random = new Random();

        List<PassEntity> passEntities = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            PassEntity passEntity = new PassEntity();
            passEntity.setPackageSeq(1);
            passEntity.setUserId("A" + 1000000 + i);
            passEntity.setStatus(PassStatus.PROGRESSED);
            passEntity.setRemainingCount(random.nextInt(11));
            passEntity.setStartedAt(now.minusDays(60));
            passEntity.setEndedAt(now.minusDays(1));
            passEntities.add(passEntity);

        }
        passRepository.saveAll(passEntities);
    }
}
