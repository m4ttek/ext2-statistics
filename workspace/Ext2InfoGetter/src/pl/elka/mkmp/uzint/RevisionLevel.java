package pl.elka.mkmp.uzint;

/**
 * Określa rewizję systemu plików.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public enum RevisionLevel {
	EXT2_GOOD_OLD_REV(0, "0 (good old)"),
	EXT2_DYNAMIC_REV(1, "1 (dynamic)");
	
	private final Integer id;
	
	private final String code;

	RevisionLevel(Integer id, String code) {
		this.id = id;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Integer getId() {
		return id;
	}
	
	public static RevisionLevel getById(Integer id) {
		for (RevisionLevel revisionLevel : RevisionLevel.values()) {
			if (revisionLevel.getId().equals(id)) {
				return revisionLevel;
			}
		}
		return null;
	}
}
