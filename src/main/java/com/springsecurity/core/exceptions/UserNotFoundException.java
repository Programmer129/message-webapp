package com.springsecurity.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "User not Found")
public class UserNotFoundException extends RuntimeException {
}
