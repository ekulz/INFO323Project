package filters;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ExceptionLogger implements ExceptionMapper<Exception> {

	private final Logger logger = LoggerFactory.getLogger(ExceptionLogger.class);

	@Override
	public Response toResponse(Exception ex) {
		// was it a WebException (which wrap HTTP error responses)?
		if (ex instanceof WebApplicationException) {
			// extract HTTP response and return it
			WebApplicationException wex = (WebApplicationException) ex;
			return wex.getResponse();
		} else {
			// if not we have a genuine exception, so log it and return a 500 response
			logger.error("Server threw an exception", ex);
			return Response.status(500).entity("Server-side error").build();
		}
	}

}
