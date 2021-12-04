package com.ngoctta.exception;

public class MyAccessDeniedException extends RuntimeException{
	public MyAccessDeniedException(String message) {
		super(message);
	}
}
