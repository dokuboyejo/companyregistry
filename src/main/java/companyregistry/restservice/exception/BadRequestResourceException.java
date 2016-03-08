package companyregistry.restservice.exception;

/**
 * @author dokuboyejo
 *
 */
public class BadRequestResourceException extends RuntimeException {

	private static final long serialVersionUID = 1206004824536296604L;

	public BadRequestResourceException() {
	}

	public BadRequestResourceException(final String msg) {
		super(msg);
	}

	public BadRequestResourceException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public BadRequestResourceException(final Throwable cause) {
		super(cause);
	}
	
}
