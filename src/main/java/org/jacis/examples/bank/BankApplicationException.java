package org.jacis.examples.bank;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BankApplicationException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public BankApplicationException(Throwable cause) {
    super(cause, toResponse(cause.getMessage()));
  }

  public BankApplicationException(String message) {
    super(toResponse(message));
  }

  private static Response toResponse(String message) {
    return Response.status(500).entity(message).type("text/plain").build();
  }

}
