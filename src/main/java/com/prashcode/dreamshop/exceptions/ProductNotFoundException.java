package com.prashcode.dreamshop.exceptions;

public class ProductNotFoundException  extends  RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
}
