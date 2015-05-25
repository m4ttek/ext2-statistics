package pl.elka.mkmp.uzint;

import java.util.Date;

/**
 * Klasa reprezentująca Superblock w systemie plików.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public class Superblock {

	private byte[] bytes;
	
	// zawiera liczbę wszystkich inodów - wolnych i zajętych
	private final Integer inodesCount;
	
	// zawiera liczbę wszystkich bloków - wolnych i zajętych
	private final Integer blocksCount;
	
	// zawiera liczbę wszystkich bloków zarezerwowanych dla super usera
	private final Integer rBlocksCount;
	
	// zawiera liczbę wszystkich wolnych bloków
	private final Integer freeBlocksCount;
	
	// zawiera liczbę wszystkich wolnych i-nodów
	private final Integer freeInodesCount;
	
	// zawiera informację w którym bloku znajduje się superblock
	private final Integer firstDataBlock;

	// zawiera informację o rozmiarze bloku
	private final Integer blockSize;
	
	// zawiera informacje o rozmiarze fragmentu
	private final Integer fragmentSize;
	
	// zawiera informację o liczbie bloków na grupę
	private final Integer blocksPerGroup;
	
	// zawiera informację o liczbie fragmentów na grupę
	private final Integer fragsPerGroup;
	
	// zawiera informację o liczbie i-nodów na grupę
	private final Integer inodesPerGroup;
	
	// zawiera informację kiedy ostatnio zamontowano system plików
	private final Date lastMountedTime;
	
	// zawiera informację kiedy ostatnio pisano w systemie plików
	private final Date lastWriteTime;
	
	// zawiera informację ile razy system plików został zamontowany od czasu pełnej weryfikacji
	private final Short mountCount;
	
	// zawiera informację ile razy system plików może zostać zamontowany do czasu pełnej weryfikacji
	private final Short leftMountCount;
	
	// zawiera informację czy jest to system plików w formacie EXT2
	private final Short magicNumber;

	// zawiera informację o stanie systemu plików
	private final Short state;

	// zawiera informację jaką czynność należy wykonać kiedy został wykryty błąd
	private final Short errors;
	
	// zawiera informację o ostatnim czasie sprawdzenia systemu plików
	private final Date lastCheckTime;

	// zawiera informację o maksymalnym czasie do następnego sprawdzenia systemu plików
	private final Integer timeInterval;

	// zawiera informację o identyfikatorze systemu, w którym system plików został stworzony
	private final Integer creatorOS;

	// zawiera informację o identyfikatorze użytkownika dla zarezerwowanych bloków
	private final Short defReservedUID;

	// zawiera informację o identyfikatorze grupy dla zarezerwowanych bloków
	private final Short defReservedGID;

	// zawiera informację, gdzie znajduje się pierwszy użyteczny węzeł
	private final Integer firstInode;

	// zawiera informację o rozmiarze jednego węzła
	private final Short inodeSize;

	// nazwa woluminu (praktycznie nieużywana)
	private final String volumeName;

	// zawiera informację o ścieżce pod którą został ostatnio zamontowany
	private final String lastMountedPath;

	// zawiera informację o indetyfikatorze rewizji systemu plików
	private final Integer revisionLevel;
	
	private Number extractNumberFromBytes(int offset, int length) {
		int number = 0;
		for (int i = offset + length - 1; i > offset; i--) {
			number |= (0xFF & bytes[i]);
			number <<= 8;
		}
		number |= (0xFF & bytes[offset]);
		return number;
	}
	
	private String extractStringFromBytes(int offset, int length) {
		return new String(bytes, offset, length);
	}
	
	public Superblock(byte[] bytes) {
		this.bytes = bytes;
		//read statistics
		inodesCount = extractNumberFromBytes(0, 4).intValue();
		blocksCount = extractNumberFromBytes(4, 4).intValue();
		rBlocksCount = extractNumberFromBytes(8, 4).intValue();
		freeBlocksCount = extractNumberFromBytes(12, 4).intValue();
		freeInodesCount = extractNumberFromBytes(16, 4).intValue();
		firstDataBlock = extractNumberFromBytes(20, 4).intValue();
		int standardByteBlockSize = 1024;
		blockSize = (standardByteBlockSize << extractNumberFromBytes(24, 4).intValue());
		fragmentSize = (standardByteBlockSize << extractNumberFromBytes(28, 4).intValue());
		blocksPerGroup = extractNumberFromBytes(32, 4).intValue();
		fragsPerGroup = extractNumberFromBytes(36, 4).intValue();
		inodesPerGroup = extractNumberFromBytes(40, 4).intValue();
		lastMountedTime = new Date((long) extractNumberFromBytes(44, 4).intValue() * 1000);
		lastWriteTime = new Date((long) extractNumberFromBytes(48, 4).intValue() * 1000);
		mountCount = extractNumberFromBytes(52, 2).shortValue();
		leftMountCount = extractNumberFromBytes(54, 2).shortValue();
		magicNumber = extractNumberFromBytes(56, 2).shortValue();
		state = extractNumberFromBytes(58, 2).shortValue();
		errors = extractNumberFromBytes(60, 2).shortValue();
		lastCheckTime = new Date((long) extractNumberFromBytes(64, 4).intValue() * 1000);
		timeInterval = extractNumberFromBytes(68, 4).intValue();
		creatorOS = extractNumberFromBytes(72, 4).intValue();
		defReservedUID = extractNumberFromBytes(80, 2).shortValue();
		defReservedGID = extractNumberFromBytes(82, 2).shortValue();
		firstInode = extractNumberFromBytes(84, 4).intValue();
		inodeSize = extractNumberFromBytes(88, 2).shortValue();
		volumeName = extractStringFromBytes(120, 16);
		lastMountedPath = extractStringFromBytes(136, 64);
		revisionLevel = extractNumberFromBytes(76, 4).intValue();
	}

	public byte[] getBytes() {
		return bytes;
	}

	public Integer getInodesCount() {
		return inodesCount;
	}

	public Integer getBlocksCount() {
		return blocksCount;
	}

	public Integer getrBlocksCount() {
		return rBlocksCount;
	}

	public Integer getFreeBlocksCount() {
		return freeBlocksCount;
	}

	public Integer getFreeInodesCount() {
		return freeInodesCount;
	}

	public Integer getFirstDataBlock() {
		return firstDataBlock;
	}

	public Integer getBlockSize() {
		return blockSize;
	}

	public Integer getFragmentSize() {
		return fragmentSize;
	}

	public Integer getBlocksPerGroup() {
		return blocksPerGroup;
	}

	public Integer getFragsPerGroup() {
		return fragsPerGroup;
	}

	public Integer getInodesPerGroup() {
		return inodesPerGroup;
	}

	public Date getLastMountedTime() {
		return lastMountedTime;
	}

	public Date getLastWriteTime() {
		return lastWriteTime;
	}

	public Short getLeftMountCount() {
		return leftMountCount;
	}

	public Short getMagicNumber() {
		return magicNumber;
	}

	public Short getMountCount() {
		return mountCount;
	}

	public Short getState() {
		return state;
	}

	public Short getErrors() {
		return errors;
	}

	public Date getLastCheckTime() {
		return lastCheckTime;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public Integer getCreatorOS() {
		return creatorOS;
	}

	public Short getDefReservedUID() {
		return defReservedUID;
	}

	public Short getDefReservedGID() {
		return defReservedGID;
	}

	public Integer getFirstInode() {
		return firstInode;
	}

	public Short getInodeSize() {
		return inodeSize;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public String getLastMountedPath() {
		return lastMountedPath;
	}

	public Integer getRevisionLevel() {
		return revisionLevel;
	}
	
}
