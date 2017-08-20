package com.revanth.openchain.exception;

/**
 * Created by revanthpobala on 7/23/17.
 */

/**
 * Custom exception class
 */
public class CustomException extends Exception {

    /**
     * Constructor
     */
    public CustomException() {
        super();
    }

    /**
     * Constructor with a message.
     *
     * @param message
     */
    public CustomException(String message) {
        super(message);
    }
}
