package companyregistry.restservice.exception;

/**
 * @author dokuboyejo
 *
 */
public class ConflictResourceException extends RuntimeException {

	private static final long serialVersionUID = -3922611599288821717L;

	public ConflictResourceException() {
	}

	public ConflictResourceException(final String msg) {
		super(msg);
	}

	public ConflictResourceException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public ConflictResourceException(final Throwable cause) {
		super(cause);
	}
	
}