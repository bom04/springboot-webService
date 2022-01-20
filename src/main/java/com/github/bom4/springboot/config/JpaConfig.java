package com.github.bom4.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // jpa auditing 활성화시키는 어노테이션
public class JpaConfig {
}
