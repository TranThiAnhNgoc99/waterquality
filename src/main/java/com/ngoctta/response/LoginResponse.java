package com.ngoctta.response;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import com.ngoctta.model.LoginResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginResponse {

	@Schema(example = "200")
	int status;
	@Nullable
	private final LoginResponseDTO data;
	@Schema(example = "Login Successfully!")
	String message;
	
	public LoginResponse(LoginResponseDTO data, int status, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;	
}

	public static LoginResponse success(LoginResponseDTO data) {
		return new LoginResponse(data, HttpStatus.OK.value(), null);
	}
	
	public static LoginResponse failure(int status) {
		switch (status) {
		case 400:
			return new LoginResponse(null, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		case 401:
			return new LoginResponse(null, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		default:
			return new LoginResponse(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}
}
