package com.kh.spring.common.exception;

public class BoardException extends RuntimeException {
	
	public BoardException() {
		super();
	}
	
	//매개변수가 있는 생성자
	public BoardException(String msg) {
		super(msg);
	}
}
