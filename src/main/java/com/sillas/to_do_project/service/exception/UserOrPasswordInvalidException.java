package com.sillas.to_do_project.service.exception;

public class UserOrPasswordInvalidException extends RuntimeException{
    public UserOrPasswordInvalidException(){
        super("User or Password Invalid");
    }
}
