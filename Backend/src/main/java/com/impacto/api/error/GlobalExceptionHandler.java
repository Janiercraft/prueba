package com.impacto.api.error;
import com.impacto.application.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponse> build(HttpStatus s,String e,String m,String path,List<ErrorResponse.FieldErrorDetail> d) {
        return ResponseEntity.status(s).body(new ErrorResponse(Instant.now(),s.value(),e,m,path,d));
    }
    @ExceptionHandler(NotFoundException.class) ResponseEntity<ErrorResponse> notFound(NotFoundException ex,HttpServletRequest r) {
        return build(HttpStatus.NOT_FOUND,"NOT_FOUND",ex.getMessage(),r.getRequestURI(),List.of());
    }
    @ExceptionHandler( {
        ConflictException.class,DomainException.class
    }
    ) ResponseEntity<ErrorResponse> conflict(RuntimeException ex,HttpServletRequest r) {
        return build(HttpStatus.CONFLICT,"BUSINESS_RULE",ex.getMessage(),r.getRequestURI(),List.of());
    }
    @ExceptionHandler(ForbiddenException.class) ResponseEntity<ErrorResponse> forbidden(ForbiddenException ex,HttpServletRequest r) {
        return build(HttpStatus.FORBIDDEN,"FORBIDDEN",ex.getMessage(),r.getRequestURI(),List.of());
    }
    @ExceptionHandler(AuthenticationException.class) ResponseEntity<ErrorResponse> unauthorized(AuthenticationException ex,HttpServletRequest r) {
        return build(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED","Credenciales inválidas",r.getRequestURI(),List.of());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex,
        HttpServletRequest r) {
        var d=ex.getBindingResult().getFieldErrors().stream().map(x->new ErrorResponse.FieldErrorDetail(x.getField(),x.getDefaultMessage())).toList();
        return build(HttpStatus.BAD_REQUEST,"VALIDATION_ERROR","La solicitud contiene errores",r.getRequestURI(),d);
    }
    @ExceptionHandler(ConstraintViolationException.class) ResponseEntity<ErrorResponse> validation2(ConstraintViolationException ex,
        HttpServletRequest r) {
        return build(HttpStatus.BAD_REQUEST,"VALIDATION_ERROR",ex.getMessage(),r.getRequestURI(),List.of());
    }
    @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponse> generic(Exception ex,HttpServletRequest r) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_ERROR","Ocurrió un error inesperado",r.getRequestURI(),List.of());
    }
}
