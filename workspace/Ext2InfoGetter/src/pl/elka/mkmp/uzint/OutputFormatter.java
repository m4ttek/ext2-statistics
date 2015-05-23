package pl.elka.mkmp.uzint;

/**
 * Klasa wyświetlająca informacje o systemie plików na konsoli.
 * 
 * @author Mateusz Kamiński
 * @author Michał Pluta
 */
public class OutputFormatter {
	
	private static final String FORMATTER = "%-40.40s  %-30.30s%n";
	
	private static final Short EXT2_SUPER_MAGIC = (short) 0xEF53;
	
	public static void printSuperblockData(String fsName, Superblock superblock) {
		System.out.printf("EXT2 Info by Mateusz Kamiński & Michał Pluta\n");
		System.out.println("Showing statistics for ext2 file system '" + fsName + "'\n");
		if (!superblock.getMagicNumber().equals(EXT2_SUPER_MAGIC)) {
			System.err.println("Loaded file is not an EXT2 file system!");
			System.exit(1);
		}
		
		System.out.printf(FORMATTER, "No. of I-Nodes (all used and free):", superblock.getInodesCount());
		System.out.printf(FORMATTER, "No. of Blocks (all used and free):", superblock.getBlocksCount());
		System.out.printf(FORMATTER, "No. of all free I-nodes:", superblock.getFreeInodesCount());
		System.out.printf(FORMATTER, "No. of all free Blocks:", superblock.getFreeBlocksCount());
		System.out.printf(FORMATTER, "First data Block (for Superblock):", superblock.getFirstDataBlock());
		System.out.printf(FORMATTER, "Block size (in Bytes):", superblock.getBlockSize());
		System.out.printf(FORMATTER, "Fragment size (in Bytes):", superblock.getFragmentSize());
		System.out.printf(FORMATTER, "Number of Blocks per group:", superblock.getBlocksPerGroup());
		System.out.printf(FORMATTER, "Number of I-Nodes per group:", superblock.getInodesPerGroup());
		System.out.printf(FORMATTER, "Number of Fragments per group:", superblock.getFragsPerGroup());
		System.out.printf(FORMATTER, "Last mount time:", superblock.getLastMountedTime());
		System.out.printf(FORMATTER, "Last write time:", superblock.getLastWriteTime());
		System.out.printf(FORMATTER, "Mount count since fully verified:", superblock.getMountCount());
		System.out.printf(FORMATTER, "Left mount count to being fully verified:", superblock.getLeftMountCount());
		System.out.printf(FORMATTER, "Filesystem magic number:", "0xEF53");
		System.out.printf(FORMATTER, "File system state:", FileSystemState.getById(superblock.getState()).getCode());
		System.out.printf(FORMATTER, "File system on error behaviour:", ErrorBehaviour.getById(superblock.getErrors()).getCode());
		System.out.printf(FORMATTER, "Last check time:", superblock.getLastCheckTime());
		System.out.printf(FORMATTER, "Check time interval:", - superblock.getTimeInterval());
		System.out.printf(FORMATTER, "OS creator:", OSCreator.getById(superblock.getCreatorOS()).getCode());
		System.out.printf(FORMATTER, "Reserved blocks UID:", superblock.getDefReservedUID() + " (user root)");
		System.out.printf(FORMATTER, "Reserved blocks GID:", superblock.getDefReservedGID() + " (group root)");
		System.out.printf(FORMATTER, "First I-Node:", superblock.getFirstInode());
		System.out.printf(FORMATTER, "I-Node size:",  - superblock.getInodeSize());
	}
	
}
