/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.exceptions;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author brendandw
 */
public class AtmException extends Exception {

    @Getter
    private String errorCode;

    public AtmException() {
        super();
    }

    @Builder
    public AtmException(String message) {
        super(message);
        
    }

    @Builder
    public AtmException(String message,String errorCode) {
        super(message);
        this.errorCode=errorCode;

    }

    @Builder
    public AtmException(String message,Throwable cause) {
        super(message,cause);
    }

    @Builder
    public AtmException(String message,String errorCode, Throwable cause) {
        super(message,cause);
        this.errorCode=errorCode;
    }
    
    
    
    
}
