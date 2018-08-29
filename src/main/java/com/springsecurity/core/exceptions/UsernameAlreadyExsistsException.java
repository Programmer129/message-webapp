package com.springsecurity.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Username already exsists!")
public class UsernameAlreadyExsistsException extends RuntimeException {
}
