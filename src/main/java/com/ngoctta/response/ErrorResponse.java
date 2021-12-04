package com.ngoctta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel
public class ErrorResponse {
	//@ApiModelProperty(value = "status", example = "status")
    private int status;
	//@ApiModelProperty(value = "data", example = "data")
    private Object data;
	//@ApiModelProperty(value = "message", example = "message")
    private String message;
}