package com.batchstudy.demo.config;

import com.batchstudy.demo.repository.pass.PassEntity;
import com.batchstudy.demo.repository.pass.PassStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ExpirePassesJobConfig {

    private final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job expirePassesJob(){
        return this.jobBuilderFactory.get("expirePassesJob")
                .start(expirePassesStep())
                .build();
    }



    @Bean
    public Step expirePassesStep(){
        return this.stepBuilderFactory.get("expirePassesStep")
                // <I, O> --> Input 데이터 , Output 데이터
                .<PassEntity, PassEntity>chunk(CHUNK_SIZE)
                .reader(expirePassesItemReader())
                .processor(expirePassesItemProcessor())
                .writer(expirePassesItemWriter())
                .build();
    }


    /**
     * 페이징 기법보다 보다 높은 성능으로 데이터 변경에 무관한 무결성 조회가 가능하다.
     *  */
    @Bean
    @StepScope
    public JpaCursorItemReader<PassEntity> expirePassesItemReader(){
        return new JpaCursorItemReaderBuilder<PassEntity>()
                .name("expirePassesItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from PassEntity p where p.status = :status and p.endedAt <= :endedAt")
                .parameterValues(Map.of("status", PassStatus.PROGRESSED, "endedAt", LocalDateTime.now()))
                .build();
    }


    // PassEntity가 들어와서 PassEntity로 나가므로 return 값을 바꾸는 것이 아니라
    // 필요한 부분만 수정한다.
    @Bean
    public ItemProcessor<PassEntity, PassEntity> expirePassesItemProcessor(){
        return passEntity ->{
            passEntity.setStatus(PassStatus.EXPIRED);
            passEntity.setExpiredAt(LocalDateTime.now());
            return passEntity;
        };
    }


    /** JPA 영속성 관리를 위해 Entity Manager를 필요로함 */

    @Bean
    public JpaItemWriter<PassEntity> expirePassesItemWriter(){
        return new JpaItemWriterBuilder<PassEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
