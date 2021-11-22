package com.example.file.upload.download.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String errorMessage;
}
