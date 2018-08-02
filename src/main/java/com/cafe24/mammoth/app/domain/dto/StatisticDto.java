package com.cafe24.mammoth.app.domain.dto;

import org.springframework.context.annotation.Configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@Getter @Setter @ToString
@EqualsAndHashCode
public class StatisticDto {
	private Long totalPanelCount;
	private Long avgPerPanelCount;
}
