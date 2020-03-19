package com.nelioalves.cursomc.exception;

public class NotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 4305911217853519810L;

    public NotFoundException(String msg) {
        super(msg);
    }

}