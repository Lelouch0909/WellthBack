package com.lontsi.wellthappback.exceptions;

import lombok.Getter;

public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException(String message) {
    super(message);
  }

}
