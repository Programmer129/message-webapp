package com.springsecurity.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ACCEPTED, value = HttpStatus.ACCEPTED, reason = "Not enough money!")
public class NotEnoughMoneyException extends RuntimeException {
}
