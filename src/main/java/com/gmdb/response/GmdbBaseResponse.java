package com.gmdb.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GmdbBaseResponse {

	private Object data;

	public GmdbBaseResponse(Object data) {
		super();
		this.data = data;
	}

}
