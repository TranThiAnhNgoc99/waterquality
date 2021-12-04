package com.ngoctta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ngoctta.response.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiResponse(
			responseCode = "404",
			description = "Not Found",
			content =@Content(
					schema = @Schema(implementation = ErrorResponse.class),
					examples = {
							@ExampleObject(
									value = "{\n\t\"status\": 404,\n\t\"data\": null,\n\t\"message\": \"Not Found\"\n}")
					}))
	public ErrorResponse handleNotFoundException( NotFoundException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), null, ex.getMessage());
	}
	
	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(
			responseCode = "500",
			description = "Internal Server Error",
			content =@Content(
					schema = @Schema(implementation = ErrorResponse.class),
					examples = {
							@ExampleObject(
									value = "{\n\t\"status\": 500,\n\t\"data\": null,\n\t\"message\": \"Internal Server Error\"\n}")
					}))
	public ErrorResponse handleInternalServerException( InternalServerException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, ex.getMessage());
	}
	
	@ExceptionHandler(MyAccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ApiResponse(
			responseCode = "403",
			description = "Access is denied",
			content =@Content(
					schema = @Schema(implementation = ErrorResponse.class),
					examples = {
							@ExampleObject(
									value = "{\n\t\"status\": 403,\n\t\"data\": null,\n\t\"message\": \"Access is denied\"\n}")
					}))
	public ErrorResponse handleAccessDeniedException( MyAccessDeniedException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.FORBIDDEN.value(), null, ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ApiResponse(
			responseCode = "401",
			description = "Unauthorized",
			content =@Content(
					schema = @Schema(implementation = ErrorResponse.class),
					examples = {
							@ExampleObject(
									value = "{\n\t\"status\": 401,\n\t\"data\": null,\n\t\"message\": \"Unauthorized\"\n}")
					}))
	public ErrorResponse handleUnauthorizedException( UnauthorizedException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), null, ex.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content =@Content(
					schema = @Schema(implementation = ErrorResponse.class),
					examples = {
							@ExampleObject(
									value = "{\n\t\"status\": 400,\n\t\"data\": null,\n\t\"message\": \"Bad Request\"\n}")
					}))
	public ErrorResponse handleBadRequestException( BadRequestException ex, WebRequest req) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception ex, WebRequest req) {
		return new ErrorResponse(ex.hashCode(), null, ex.getMessage());
	}
}