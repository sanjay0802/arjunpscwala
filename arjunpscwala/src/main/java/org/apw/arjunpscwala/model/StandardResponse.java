package org.apw.arjunpscwala.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StandardResponse<T> {

    private String msg;
    private Integer status;
    private T response;


    public static <T> StandardResponse<T> success(T data,String msg,Integer status){
      return new StandardResponse<T>(msg, status, data);
    }

    public static <T> StandardResponse<T> failure(String msg,Integer status){
        return new StandardResponse<T>(msg, status,null);
    }




}
