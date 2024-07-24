package com.chervonnaya.objectmappermvcproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnableToDeleteException extends RuntimeException{
    private String entityClass;
    private Long id;
}