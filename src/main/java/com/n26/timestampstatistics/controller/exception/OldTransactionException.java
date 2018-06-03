package com.n26.timestampstatistics.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT) // 204
public class OldTransactionException extends Exception {
}
