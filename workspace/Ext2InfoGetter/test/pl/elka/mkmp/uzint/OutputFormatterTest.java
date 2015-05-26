package pl.elka.mkmp.uzint;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class OutputFormatterTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream systemOut = System.out;
	
	@Before
	public void init() {
		//capture default output and error streams
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		
		//disable System.exit()
		System.setSecurityManager(new StopExitSecurityManager());
	}
	
	@Test
	public void testNoFilesystemProvidedDisplaysError() {
		systemOut.println("Wywołanie programu bez parametru:\n");
		try {
			Main.main(new String[0]);
			fail();
		} catch(SystemExitCallException e) {
			systemOut.println("Program zakończył się statusem " + e.getExitStatus());
			systemOut.println("Program zwrócił błąd: " + errContent.toString());
			assertEquals(1, e.getExitStatus());
			assertEquals("No FS filename provided\n", errContent.toString());
		}
	}
	
	@Test
	public void testProvidingNonExistingFileDisplaysError() {
		systemOut.println("Wywołanie programu ze ścieżką do nieistniejącego pliku:\n");
		try {
			String[] args = { "fakefile" };
			Main.main(args);
			fail();
		} catch(SystemExitCallException e) {
			systemOut.println("Program zakończył się statusem " + e.getExitStatus());
			systemOut.println("Program zwrócił błąd: " + errContent.toString());
			assertEquals(2, e.getExitStatus());
			assertEquals("File not found!\n", errContent.toString());
		}
	}
	
	@Test
	public void testNotExt2FileDisplaysError() {
		systemOut.println("Wywołanie programu ze ścieżką do pliku somefile.zip:\n");
		try {
			String[] args = { "somefile.zip" };
			Main.main(args);
			fail();
		} catch(SystemExitCallException e) {
			systemOut.println("Program zakończył się statusem " + e.getExitStatus());
			systemOut.println("Program zwrócił błąd: " + errContent.toString());
			assertEquals(4, e.getExitStatus());
			assertEquals("Loaded file is not an EXT2 file system!\n", errContent.toString());
		}
	}
	
	@Test
	public void testProvidingExt2FileYieldsProperStats() {
		systemOut.println("Wywołanie programu ze ścieżką do pliku zawierającego system plików EXT2:\n");
		String[] args = { "filesystem" };
		Main.main(args);
		systemOut.println("Program zakończył się normalnie");
		String programOut = outContent.toString();
		systemOut.println("Zwrócone statystyki:\n\n====================");
		systemOut.println(programOut + "====================\n");
		systemOut.println("Wywołanie programu dumpe2fs");
		String dumpe2fsOut;
		try {
			dumpe2fsOut = executeDumpe2fs("filesystem");
		} catch(Exception e) {
			systemOut.println("Nie można wywołac programu dumpe2fs. Test przerwany.");
			return;
		}
		systemOut.println("dumpe2fs zwrócił następujące informacje:\n\n====================\n");
		systemOut.println(dumpe2fsOut + "====================\n");
		systemOut.println("Automatyczne analizowanie wyjścia programu");
		checkStat("No. of I-Nodes (all used and free)", "Inode count", programOut, dumpe2fsOut);
		checkStat("No. of Blocks (all used and free)", "Block count", programOut, dumpe2fsOut);
		checkStat("No. of all free I-nodes", "Free inodes", programOut, dumpe2fsOut);
		checkStat("No. of all free Blocks", "Free blocks", programOut, dumpe2fsOut);
		checkStat("First data Block (for Superblock)", "First block", programOut, dumpe2fsOut);
		checkStat("Block size (in Bytes)", "Block size", programOut, dumpe2fsOut);
		checkStat("Fragment size (in Bytes)", "Fragment size", programOut, dumpe2fsOut);
		checkStat("Number of Blocks per group", "Blocks per group", programOut, dumpe2fsOut);
		checkStat("Number of I-Nodes per group", "Inodes per group", programOut, dumpe2fsOut);
		checkStat("Number of Fragments per group", "Fragments per group", programOut, dumpe2fsOut);
		try {
			checkDateStat("Last mount time", "Last mount time", programOut, dumpe2fsOut);
		} catch (ParseException e1) {
			//OK, then compare as non-dates
			checkStat("Last mount time", "Last mount time", programOut, dumpe2fsOut);
		}
		try {
			checkDateStat("Last write time", "Last write time", programOut, dumpe2fsOut);
		} catch (ParseException e1) {
			//OK, then compare as non-dates
			checkStat("Last write time", "Last write time", programOut, dumpe2fsOut);
		}
		checkStat("Mount count since fully verified", "Mount count", programOut, dumpe2fsOut);
		checkStat("Left mount count to being fully verified", "Maximum mount count", programOut, dumpe2fsOut);
		checkStat("Filesystem magic number", "Filesystem magic number", programOut, dumpe2fsOut);
		checkStat("File system state", "Filesystem state", programOut, dumpe2fsOut);
		checkStat("File system on error behaviour", "Errors behavior", programOut, dumpe2fsOut);
		try {
			checkDateStat("Last check time", "Last checked", programOut, dumpe2fsOut);
		} catch (ParseException e) {
			//OK, then compare as non-dates
			checkStat("Last check time", "Last checked", programOut, dumpe2fsOut);
		}
		checkStat("Check time interval", "Check interval", programOut, dumpe2fsOut);
		checkStat("OS creator", "Filesystem OS type", programOut, dumpe2fsOut);
		checkStat("Reserved blocks UID", "Reserved blocks uid", programOut, dumpe2fsOut);
		checkStat("Reserved blocks GID", "Reserved blocks gid", programOut, dumpe2fsOut);
		checkStat("First I-Node", "First inode", programOut, dumpe2fsOut);
		checkStat("I-Node size", "Inode size", programOut, dumpe2fsOut);
		checkStat("Volume name", "Filesystem volume name", programOut, dumpe2fsOut);
		checkStat("Last mounted on", "Last mounted on", programOut, dumpe2fsOut);
		checkStat("Filesystem revision #", "Filesystem revision #", programOut, dumpe2fsOut);
		systemOut.println("\nWszystkie testy przebiegły pomyślnie!");
	}
	
	private void checkDateStat(String programStatName, String dumpe2fsStatName, String programOut, String dumpe2fsOut) throws ParseException {
		String programStatValue = getStatValue(programStatName, programOut);
		String dumpe2fsStatValue = getStatValue(dumpe2fsStatName, dumpe2fsOut);
		DateFormat programFormat = new SimpleDateFormat("EEE MMMM dd HH:mm:ss z yyyy", Locale.US);
		DateFormat dumpe2fsFormat = new SimpleDateFormat("EEE MMMM dd HH:mm:ss yyyy", Locale.US);
		Date programDate = programFormat.parse(programStatValue);
		Date dumpe2fsDate = dumpe2fsFormat.parse(dumpe2fsStatValue);
		systemOut.println("\n" + programStatName);
		systemOut.println("Ext2InfoGetter: " + programStatValue);
		systemOut.println("dumpe2fs: " + dumpe2fsStatValue);
		assertEquals(programDate,dumpe2fsDate);
		systemOut.println("Zgodne!");
	}
	
	private void checkStat(String programStatName, String dumpe2fsStatName, String programOut, String dumpe2fsOut) {
		String programStatValue = getStatValue(programStatName, programOut);
		String dumpe2fsStatValue = getStatValue(dumpe2fsStatName, dumpe2fsOut);
		systemOut.println("\n" + programStatName);
		systemOut.println("Ext2InfoGetter: " + programStatValue);
		systemOut.println("dumpe2fs: " + dumpe2fsStatValue);
		assertEquals(programStatValue, dumpe2fsStatValue);
		systemOut.println("Zgodne!");
	}
	
	private String executeDumpe2fs(String filePath) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec("dumpe2fs " + filePath);
		proc.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		StringBuilder output = new StringBuilder();
		String buf;
		while((buf = reader.readLine()) != null) {
			output.append(buf + "\n");
		}
		return output.toString();
	}
	
	private String getStatValue(String statName, String allStats) {
		int statIndex = allStats.indexOf(statName) + statName.length() + 1;
		while(allStats.charAt(statIndex) == ' ') {
			statIndex++;
		}
		StringBuilder statValue = new StringBuilder();
		while(allStats.charAt(statIndex) != '\n') {
			statValue.append(allStats.charAt(statIndex));
			statIndex++;
		}
		return statValue.toString().trim();
	}

}
