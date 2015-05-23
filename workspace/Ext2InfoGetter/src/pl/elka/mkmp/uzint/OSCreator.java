package pl.elka.mkmp.uzint;

/**
 * Identyfikuje system operacyjny, na którym powstał system plików.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public enum OSCreator {
	EXT2_OS_LINUX(0, "Linux"),
	EXT2_OS_HURD(1, "GNU HURD"),
	EXT2_OS_MASIX(2, "MASIX"),
	EXT2_OS_FREEBSD(3, "FreeBSD"),
	EXT2_OS_LITES(4, "Lites");
	
	private final Integer id;
	
	private final String code;

	OSCreator(Integer id, String code) {
		this.id = id;
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public static OSCreator getById(Integer id) {
		for (OSCreator osCreator : OSCreator.values()) {
			if (osCreator.getId().equals(id)) {
				return osCreator;
			}
		}
		return null;
	}
}
