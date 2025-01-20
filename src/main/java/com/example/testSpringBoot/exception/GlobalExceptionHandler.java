package com.example.testSpringBoot.exception;

import com.example.testSpringBoot.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private  static final String MIN_ATTRIBUTE = "min";
    // Xử lý RuntimeException
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<String>> handlingRuntimeException(RuntimeException exception) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        // Trả về một ResponseEntity với kiểu ApiResponse<String>
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        // Trả về một ResponseEntity với kiểu ApiResponse<String>
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                .build());
    }

    // Xử lý MethodArgumentNotValidException (khi dữ liệu không hợp lệ)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handlingValidation(MethodArgumentNotValidException exception) {
            String enumKey = exception.getFieldError().getDefaultMessage();

                ErrorCode errorCode = ErrorCode.valueOf(enumKey);
                Map<String, Object> attributes = null;
            try {
                var contrainViolation = exception.getBindingResult()
                        .getAllErrors().getFirst().unwrap(ConstraintViolation.class);
                 attributes = contrainViolation.getConstraintDescriptor().getAttributes();
                log.info(attributes.toString());
            } catch (IllegalArgumentException e){

            }
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(Objects.nonNull(attributes) ?
                    mapAttribute(errorCode.getMessage(), attributes)
                    : errorCode.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
    }
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE)) ;
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
