package org.apw.arjunpscwala.exception;

import org.apw.arjunpscwala.entity.User;

public class ApwException extends RuntimeException{

    public  User user;


    public  ApwException(String msg, User user){
        super(msg);
        this.user=user;


    }




}
