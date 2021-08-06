package com.company.wishlist.adapter.input.springrestcontroller.handler;

import com.company.wishlist.adapter.input.springrestcontroller.dto.ErrorDTO;
import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        log.debug("Not Found error: " + ex.getMessage());

        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getStatus());
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<?> handleBadRequestException(final InvalidDataException ex) {
        log.debug("Bad Request error: " + ex.getMessage());

        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleInternalServerException(final Exception ex) {
        var errorId = UUID.randomUUID().toString();

        log.error("Internal Server error " + errorId, ex);

        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Error " + errorId)
                .build();

        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getStatus());
    }

}
