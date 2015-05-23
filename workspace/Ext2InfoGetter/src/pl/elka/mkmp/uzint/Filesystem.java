package pl.elka.mkmp.uzint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Klasa reprezentująca system plików.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public class Filesystem {
	
	private RandomAccessFile file;
	
	private final Superblock superblock;
	
	public Filesystem(String path) throws FileNotFoundException, NotExt2Exception {
		File f = new File(path);
		file = new RandomAccessFile(f, "r");
		try {
			superblock = Ext2Utils.getSuperblock(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NotExt2Exception("Nie można wczytać superbloku");
		}
	}

	public Superblock getSuperblock() {
		return superblock;
	}
}
