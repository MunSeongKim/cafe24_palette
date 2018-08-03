package com.cafe24.mammoth.app.domain.dto;

import org.springframework.context.annotation.Configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@Getter @Setter @ToString
//@AllArgsConstructor
@EqualsAndHashCode
public class StatisticDto {
	private Long functionId;
	private String functionName;
	private Long functionCount;
}
