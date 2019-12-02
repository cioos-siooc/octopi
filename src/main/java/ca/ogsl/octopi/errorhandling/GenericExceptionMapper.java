package ca.ogsl.octopi.errorhandling;

import ca.ogsl.octopi.util.AppConstants;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger logger = LogManager.getLogger();

  public Response toResponse(Throwable ex) {

    ErrorMessage errorMessage = new ErrorMessage();
    setHttpStatus(ex, errorMessage);
    errorMessage.setMessage(ex.getMessage());
    errorMessage.setLink(AppConstants.PORTAL_URL);
    logger.error("Generic error caught", ex);

    return Response.status(errorMessage.getStatus())
        .entity(errorMessage)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
    if (ex instanceof WebApplicationException) {
      Integer status = ((WebApplicationException) ex).getResponse().getStatus();
      errorMessage.setStatus(status);
    } else {
      errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
          .getStatusCode()); //defaults to internal server error 500
    }
  }
}

