package com.github.bom4.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 모든 필드에 get메소드를 자동 생성함
@RequiredArgsConstructor // 모든 final 필드만 포함된 생성자를 생성함
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
