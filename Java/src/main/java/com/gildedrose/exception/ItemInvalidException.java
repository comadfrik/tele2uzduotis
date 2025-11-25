package com.gildedrose.exception;

public class ItemInvalidException extends RuntimeException{
    public ItemInvalidException(String message) {
        super(message);
    }
}
