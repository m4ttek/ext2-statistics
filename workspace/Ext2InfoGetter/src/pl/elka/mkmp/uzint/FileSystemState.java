package pl.elka.mkmp.uzint;

/**
 * Określa możliwe stany systemu plików.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public enum FileSystemState {
	/**
	 * Unmounted cleanly
	 */
	EXT2_VALID_FS((short) 1, "clean"),
	/**
	 * Errors detected
	 */
	EXT2_ERROR_FS((short) 2, "error");
	
	private final Short id;
	
	private final String code;

	FileSystemState(Short id, String code) {
		this.id = id;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Short getId() {
		return id;
	}
	
	public static FileSystemState getById(Short id) {
		for (FileSystemState fileSystemState : FileSystemState.values()) {
			if (fileSystemState.getId().equals(id)) {
				return fileSystemState;
			}
		}
		return null;
	}
}
