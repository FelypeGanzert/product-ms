package com.felypeganzert.productms.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleRecursoNaoEncontradoException(RecursoNaoEncontradoException e) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors()
                                            .stream()
                                            .map(fieldError -> fieldError.getDefaultMessage())
                                            .collect(Collectors.joining("; "));

        return new ApiError(HttpStatus.BAD_REQUEST.value(), errors);
    }
    
}
