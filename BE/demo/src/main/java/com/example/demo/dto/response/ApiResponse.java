
package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author Admin
 * @param 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class ApiResponse<T> {
    @Builder.Default
    private int code = 1000;   
    private String message;
    private T result;
    
}
