package com.devsuperior.dscatalog.services.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String msg){
        // send the msg to constructor of the superclass (RuntimeException)
        super(msg);
    }

}
