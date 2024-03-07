package com.github.rating.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

//    @ExceptionHandler(ResourcesNotFoundException.class)
//    public ResponseBody<Map<String,String>> handlerResourceNotFoundException(ResourcesNotFoundException ex){
//
//        Map<String,String> response = new HashMap<>();
//        response.put("message",ex.getMessage());
//        response.put("success",false);
//        response.put("status",HttpStatus.NOT_FOUND);
//
//    }
}
