package com.sillas.to_do_project.service.exception;

public class TaskIsEmptyException extends RuntimeException{
    public TaskIsEmptyException(){
        super("Task is empty");
    }
}
