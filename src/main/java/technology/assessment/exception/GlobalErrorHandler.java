package technology.assessment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import technology.assessment.model.dto.response.ApiResponse;

import static technology.assessment.util.AppCode.*;
import static technology.assessment.util.MessageUtil.*;


@ControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity handleRecordNotFoundExceptions(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestExceptions(BadRequestException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST, exception.getMessage()));

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegaException(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, exception.getMessage()));

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handlerGlobalError(Exception exception) {

        exception.printStackTrace();
        log.warn("An error occur  {}", exception.fillInStackTrace());
        return ResponseEntity.ok(new ApiResponse<>(FAILED, ERROR_CODE, INTERNAL_SERVER_ERROR));
    }


}

