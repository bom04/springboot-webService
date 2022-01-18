package com.github.bom4.springboot.web;

import com.github.bom4.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 json을 반환하는 컨트롤러로 만들어줌
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,@RequestParam("amount") int amount) {
        HelloResponseDto dto=new HelloResponseDto(name,amount);
        dto.setAa("123");
        return dto;
    }
}
