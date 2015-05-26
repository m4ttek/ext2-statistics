package pl.elka.mkmp.uzint;

public class SystemExitCallException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int exitStatus;
	
	public int getExitStatus() {
		return exitStatus;
	}

	public SystemExitCallException(int status) {
		exitStatus = status;
	}
	
}
