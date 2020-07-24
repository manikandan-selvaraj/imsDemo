package com.cognizant.ims.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBean<T> {

	private String message;
	private int returnCode;
	private T data;

}
