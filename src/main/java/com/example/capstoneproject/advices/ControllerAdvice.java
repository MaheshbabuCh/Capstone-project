package com.example.capstoneproject.advices;

import com.example.capstoneproject.dtos.ErrorDto;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClient;

@org.springframework.web.bind.annotation.ControllerAdvice()
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorDto> notFoundErrorResponse(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        errorDto.setErrorCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    private  ResponseEntity<ErrorDto> badRequestError(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        errorDto.setErrorCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
