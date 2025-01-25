package com.rahul.user_service.exception.payload;


import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
}