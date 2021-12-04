package com.ngoctta.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginResponseDTO {
	@Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEiLCJpYXQiOjE2MzQ3MTQyNjcsImV4cCI6MTYzNDgwMDY2N30.y3-scJdgQCRi1U2Wz0EkvVq3jrNukwkxHUuxT27dHMkPq9f9XQhQxbB3P1HJ8afZgxnDByLfYX7MULbJ1d5SBQ", required = true)
	private String token;
	@Schema(example = "Bearer")
	private String type = "Bearer";
	@Schema(example = "1", required = true)
	private Long id;
	@Schema(example = "admin1", required = true)
	private String username;
	@Schema(example = "ROLE_ADMIN", required = true)
	private List<String> roles;
	
	public LoginResponseDTO(String token, Long id, String username, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}
