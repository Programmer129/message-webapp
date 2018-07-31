package com.springsecurity.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED, code = HttpStatus.ALREADY_REPORTED, reason = "User not Found")
public class UserNotFoundException extends RuntimeException {
}
