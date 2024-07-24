package com.chervonnaya.objectmappermvcproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {
    private String entityClass;
    private Long id;
}
