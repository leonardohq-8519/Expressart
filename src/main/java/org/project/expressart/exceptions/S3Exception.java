package org.project.expressart.exceptions;

public class S3Exception extends RuntimeException {
    public S3Exception(String message, Throwable reason) {
        super(message, reason);
    }
}
