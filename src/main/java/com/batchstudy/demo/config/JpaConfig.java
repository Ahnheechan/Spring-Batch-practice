package com.batchstudy.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
}

/** 1. JPA config로 Spring IOC 컨테이너에 주입할 것은 메서드는 없고 @EnableJpaAuditing만을 붙여주면 된다.
 *  2. 원래는 그냥 애플리케이션 클래스위에 붙여서 해당 기능을 이 애플리케이션에서 사용한다고 명시해서 사용했는데
 *  3. Config class 파일을 만들어서 개발할 때 이런 설정을 사용했다는 사실을 명시 해줬다는 점에서 앞으로 이런 방식을
 *  4. 이용해야겠다는 생각이 들었다.
 *
 * */