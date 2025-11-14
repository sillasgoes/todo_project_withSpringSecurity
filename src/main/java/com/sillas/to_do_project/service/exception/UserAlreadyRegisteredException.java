package com.sillas.to_do_project.service.exception;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String username){
        super("User " + username + " is already registered");
    }
}
