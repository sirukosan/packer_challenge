package com.mobiquityinc.packer.exception;

import static java.text.MessageFormat.format;

public class APIException extends Exception {
    private Error error;

    public APIException(Error error, Object ... args) {
        super(format(error.toString(), args));
        this.error = error;
    }

    public APIException(Throwable cause, Error error, Object ... args) {
        super(format(error.toString(), args), cause);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
