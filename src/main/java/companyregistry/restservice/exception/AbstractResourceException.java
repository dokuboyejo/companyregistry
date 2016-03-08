package companyregistry.restservice.exception;

/**
 * @author dokuboyejo
 *
 */
@SuppressWarnings("serial")
public class AbstractResourceException extends RuntimeException {

	public AbstractResourceException() {
	}

	public AbstractResourceException(final String msg) {
		super(msg);
	}

	public AbstractResourceException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public AbstractResourceException(final Throwable cause) {
		super(cause);
	}

}