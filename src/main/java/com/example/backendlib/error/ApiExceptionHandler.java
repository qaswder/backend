package com.example.backendlib.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConflictResourceException.class})
    public ResponseEntity<Object> handleConflictResourceException(Exception ex, WebRequest req){
        return this.createErrorResponse(ex, req, CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest req){
        return this.createErrorResponse(ex, req, NOT_FOUND);
    }

    protected ResponseEntity<Object> createErrorResponse(Exception ex, WebRequest request, HttpStatusCode status) {
        String error = ex.getLocalizedMessage();
        if (ex.getCause() != null){
            error += String.format(". %s", ex.getCause().getMessage());
        } else if (StringUtils.isEmpty(error)){
            error = "Message not available";
        }
        final ApiErrorResponse response = ApiErrorResponse.valueOf(status.value(), getPath(request), error, ex.getClass().getName());
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request)
                .getRequest()
                .getRequestURI();
    }
}
