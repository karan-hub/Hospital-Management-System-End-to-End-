package com.csf.Hospital_Management_System2.Dot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T > {
    private boolean success;
    private String message;
    private T data;
//    private LocalDateTime timeStamp  = LocalDateTime.now() ;

}
