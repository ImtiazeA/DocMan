package me.imtiaze.docman.controller;

import me.imtiaze.docman.exception.DocNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions occurred in controller
 */
@ControllerAdvice
public class DocControllerAdvice {

    /**
     * Handles when no doc was found with ID. Creates a new header, set content type to text, and send a 404 NOT FOUND status
     */
    @ExceptionHandler(DocNotFoundException.class)
    public ResponseEntity<String> docNotFoundHandler(DocNotFoundException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>("Invalid Doc ID: " + e.getMessage(), headers, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other exceptions. Creates a new header, set content type to text, and send a 404 Bad Request status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> invalidRequest(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>("Bad Request - " + e.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

}