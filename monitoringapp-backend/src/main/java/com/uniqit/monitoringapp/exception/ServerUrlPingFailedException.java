package com.uniqit.monitoringapp.exception;

import org.springframework.http.HttpStatus;

public class ServerUrlPingFailedException extends BaseWebException {
    static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    static final String ERROR_CODE = "ERR_500_SERVER_URL_PING_FAILED";

    static final String DESCRIPTION = "Server url ping failed";

    public ServerUrlPingFailedException(Exception e) {
        super(e, status, ERROR_CODE, DESCRIPTION);
    }
}
