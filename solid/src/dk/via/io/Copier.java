package dk.via.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class Copier {
	private Reader in;
	private Writer out;
	
	public Copier(Reader in, Writer out) {
		this.in = in;
		this.out = out;
	}
	
	public void copy() throws IOException {
		BufferedReader reader = new BufferedReader(in);
		PrintWriter writer = new PrintWriter(out);
		String line = reader.readLine();
		while(line != null) {
			writer.println(line);
			line = reader.readLine();
		}
		reader.close();
		writer.close();
	}
}
