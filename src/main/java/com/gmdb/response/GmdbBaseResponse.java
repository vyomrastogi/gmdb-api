package com.gmdb.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GmdbBaseResponse {

	private Object data;
	private List<String> errorMessages = new ArrayList<>();

	public GmdbBaseResponse(Object data) {
		super();
		this.data = data;
	}

}
