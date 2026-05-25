package org.project.expressart;

import org.project.expressart.exceptions.ResourceNotFoundException;
import org.project.expressart.exceptions.*;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ProblemDetail handleUnauthorizedActionException(UnauthorizedActionException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(403);
        problemDetail.setTitle("Unauthorized Action");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(InvalidStateTransitionException.class)
    public ProblemDetail handleInvalidStateTransitionException(InvalidStateTransitionException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Invalid State Transition");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(InvalidFileException.class)
    public ProblemDetail handleInvalidFileException(InvalidFileException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Invalid File");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ImageLimitException.class)
    public ProblemDetail handleImageLimitException(ImageLimitException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(422);
        problemDetail.setTitle("Image Limit Exceeded");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(S3Exception.class)
    public ProblemDetail handleS3Exception(S3Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(507);
        problemDetail.setTitle("Storage Service Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(409);
        problemDetail.setTitle("Resource Conflict / Already Exists");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ProblemDetail handlePaymentProcessingException(PaymentProcessingException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(402);
        problemDetail.setTitle("Payment Processing Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequest(BadRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}