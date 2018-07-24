package com.springsecurity.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Not Authenticated!")
public class UserNotAuthenticatedException extends RuntimeException {
}
