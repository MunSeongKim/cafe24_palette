package com.cafe24.mammoth.app.domain.dto;

import org.springframework.context.annotation.Configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 통계 정보를 전달하는 DTO
 * @author Allen
 *
 */
@Configuration
@Getter @Setter @ToString
@EqualsAndHashCode
public class Statistic {
	private Long functionId;
	private String functionName;
	private Long functionCount;
}
