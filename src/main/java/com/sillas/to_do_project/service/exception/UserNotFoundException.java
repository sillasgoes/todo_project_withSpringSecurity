package com.sillas.to_do_project.service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("User: "+username + "not found");
    }
}
