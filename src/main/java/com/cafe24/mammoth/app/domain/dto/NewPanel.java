package com.cafe24.mammoth.app.domain.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NewPanel {
	private Map<String, String> funcs;
}
