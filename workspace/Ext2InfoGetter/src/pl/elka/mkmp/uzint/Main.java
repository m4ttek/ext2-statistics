package pl.elka.mkmp.uzint;

import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("No FS filename provided");
			System.exit(1);
		}
		String fileName = args[0];
		Filesystem filesystem = null;
		try {
			filesystem = new Filesystem(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
			System.exit(2);
		} catch (NotExt2Exception e) {
			System.err.println("Given file is not an ext2 file!");
			System.exit(3);
		}
		OutputFormatter.printSuperblockData(fileName, filesystem.getSuperblock());
	}
}
