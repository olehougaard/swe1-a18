package dk.via.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileCopier {
	private File inFile;
	private File outFile;
	
	public FileCopier(File inFile, File outFile) {
		this.inFile = inFile;
		this.outFile = outFile;
	}
	
	public void copyFile() throws IOException {
		FileReader fileReader = new FileReader(inFile);
		FileWriter fileWriter = new FileWriter(outFile);
		BufferedReader reader = new BufferedReader(fileReader);
		PrintWriter writer = new PrintWriter(fileWriter);
		String line = reader.readLine();
		while(line != null) {
			writer.println(line);
			line = reader.readLine();
		}
		reader.close();
		writer.close();
	}
}
