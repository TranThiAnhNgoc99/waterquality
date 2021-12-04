package com.ngoctta.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {
	@Schema(example = "1")
	private Long user_id;
	@Schema(example = "admin1")
	private String username;
	@Schema(example = "123")
	private String password;
}
