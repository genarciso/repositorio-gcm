package br.ufrn.imd.bancovestido.exception;

public class InvalidValueException extends Exception {
    public InvalidValueException() {
    }

    public InvalidValueException(String message) {
        super(message);
    }
}
