package com.github.braians.springblog.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	private String entityName;
    private String fieldName;   
    private Object fieldValue;


    public ResourceAlreadyExistException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("%s already exist with %s : %s", entityName, fieldName, fieldValue));
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
