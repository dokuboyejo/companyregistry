package companyregistry.restservice.exception;

/**
 * @author dokuboyejo
 *
 */
public class ServerErrorResourceException extends RuntimeException {

	private static final long serialVersionUID = -6346344492693500906L;

	public ServerErrorResourceException() {
	}

	public ServerErrorResourceException(final String msg) {
		super(msg);
	}

	public ServerErrorResourceException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public ServerErrorResourceException(final Throwable cause) {
		super(cause);
	}
	
}