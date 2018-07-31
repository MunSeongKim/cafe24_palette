package com.cafe24.mammoth.app.domain.dto;

import java.util.List;

import com.cafe24.mammoth.app.domain.Function;
import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.domain.Theme;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Palette {
	private Panel panel;
	private Theme theme;
	private List<Function> functions;
}
