package com.gmail.netcracker.application.exception;

public final class BadRequestException extends RuntimeException {
    /**
     * Конструктр без параметров.
     */
    public BadRequestException() {
        super();
    }

    /**
     * Конструкто с параметром.
     *
     * @param message Сообщение исключения.
     */
    public BadRequestException(final String message) {
        super(message);
    }
}
