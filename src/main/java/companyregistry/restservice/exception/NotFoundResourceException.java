package companyregistry.restservice.exception;

/**
 * @author dokuboyejo
 *
 */
public class NotFoundResourceException extends RuntimeException {

	private static final long serialVersionUID = 5275238144912163414L;

	public NotFoundResourceException() {
	}

	public NotFoundResourceException(final String msg) {
		super(msg);
	}

	public NotFoundResourceException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public NotFoundResourceException(final Throwable cause) {
		super(cause);
	}
	
}