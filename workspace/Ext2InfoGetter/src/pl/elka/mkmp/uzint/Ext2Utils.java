package pl.elka.mkmp.uzint;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public class Ext2Utils {

	private static final int SUPERBLOCK_LENGTH = 1024;
	private static final int SUPERBLOCK_OFFSET = 1024;
	
	public static Superblock getSuperblock(RandomAccessFile file) throws IOException {
		byte[] superblock = new byte[SUPERBLOCK_LENGTH];
		file.seek(SUPERBLOCK_OFFSET);
		file.readFully(superblock);
		return new Superblock(superblock);
	}
	
}
