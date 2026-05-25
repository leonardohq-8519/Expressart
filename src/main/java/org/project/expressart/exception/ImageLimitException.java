package org.project.expressart.exception;

public class ImageLimitException extends RuntimeException {
    public ImageLimitException(String mensaje) {
        super(mensaje);
    }
}