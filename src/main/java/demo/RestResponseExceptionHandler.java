package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    protected ResponseEntity<String> handleServerError(Exception ex, WebRequest request) throws JsonProcessingException {
        return getObjectResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL ERROR");
    }


    private ResponseEntity<String> getObjectResponseEntity(Exception ex, WebRequest request, HttpStatus httpStatus, String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SimpleRestError restError = new SimpleRestError(objectMapper);
        restError.setError(httpStatus.getReasonPhrase());
        restError.setException(ex.getClass().getName());
        restError.setMessage(ex.getMessage());
        restError.setStatus(httpStatus.value());
        restError.setCode(code);
        restError.setTimestamp(new Date().getTime());

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(restError.toJson(), headers, httpStatus);
    }

}