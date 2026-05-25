package org.project.expressart.exceptions;

public class ImageLimitException extends RuntimeException {
    public ImageLimitException(String mensaje) {
        super(mensaje);
    }
}