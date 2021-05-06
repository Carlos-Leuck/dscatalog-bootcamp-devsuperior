package com.devsuperior.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg){
        // send the msg to constructor of the superclass (RuntimeException)
        super(msg);
    }

}
