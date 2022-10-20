package com.customer;

public class CardExistException extends Exception {
    public CardExistException(String errorMessage) {
        super(errorMessage);
    }
}

