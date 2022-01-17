package com.github.bom4.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성 모두 자동으로 설정됨
public class Application {
    public static void main(String[] args) {
        // 1) SpringApplcation.run(): 내장 WAS(웹 애플리케이션 서버)를 실행
        // 2) 내장 was: 별도로 외부에 tomcat같은 WAS를 두지 않고 내부에서 실행함
        // 덕분에 외부 WAS를 설치할 필요도 없고 스프링 부트로 만들어진 jar(실행 가능한 java 패키징 파일)을 실행하면 되니
        // 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있음
        SpringApplication.run(Application.class,args);
    }
}
