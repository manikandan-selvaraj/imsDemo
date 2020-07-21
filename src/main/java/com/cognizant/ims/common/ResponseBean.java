package com.cognizant.ims.common;

public class ResponseBean<T> {

	public static class ResponseBeanBuilder<T> {

		private String message;
		private int returnCode;
		private T data;

		public ResponseBeanBuilder() {
			this.returnCode = 0;
		}

		public ResponseBean<T> build() {
			return new ResponseBean<>(this);
		}

		public ResponseBeanBuilder<T> setData(T data) {
			this.data = data;
			return this;
		}

		public ResponseBeanBuilder<T> setReturnCode(int returnCode) {
			this.returnCode = returnCode;
			return this;
		}

		public ResponseBeanBuilder<T> setMessage(String message) {
			this.message = message;
			return this;
		}

	}

	private String message;
	private int returnCode;
	private T data;

	public ResponseBean() {
	}

	private ResponseBean(ResponseBeanBuilder<T> builder) {
		this.message = builder.message;
		this.returnCode = builder.returnCode;
		this.data = builder.data;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

}
