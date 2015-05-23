package pl.elka.mkmp.uzint;

/**
 * Określa zachowanie dla driver'a w przypadku wystąpienia błedu.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public enum ErrorBehaviour {
	/**
	 * continue as if nothing happened
	 */
	EXT2_ERRORS_CONTINUE((short) 1, "Continue"),
	/**
	 * remount read-only
	 */
	EXT2_ERRORS_RO((short) 2, "Read"),
	/**
	 * cause a kernel panic
	 */
	EXT2_ERRORS_PANIC((short) 3, "Panic");
	
	private final Short id;
	
	private final String code;
	
	ErrorBehaviour(Short id, String code) {
		this.id = id;
		this.code = code;
	}

	public Short getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public static ErrorBehaviour getById(Short id) {
		for (ErrorBehaviour errorBehaviour : ErrorBehaviour.values()) {
			if (errorBehaviour.getId().equals(id)) {
				return errorBehaviour;
			}
		}
		return null;
	}
}
