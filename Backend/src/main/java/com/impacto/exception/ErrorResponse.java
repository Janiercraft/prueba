package com.impacto.exception;
import java.time.Instant;
import java.util.*;
public record ErrorResponse(Instant timestamp,int status,String error,String message,String path,List<FieldErrorDetail> details) {
    public record FieldErrorDetail(String field,String message) {
    }
}
