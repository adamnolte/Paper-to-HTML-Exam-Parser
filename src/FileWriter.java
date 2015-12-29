package mzon.adam.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileWriter {
	private String filePath;
	private Charset ENCODING;
	
	FileWriter(String path, Charset ENCODING){
		this.filePath = path;
		this.ENCODING = ENCODING;
	}
	
	public void writeFile(ArrayList<String> fileText) throws IOException{
		Path path = Paths.get(filePath);
		try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
			for(String line : fileText){
				writer.write(line);
				writer.newLine();
			}
		}
	}
}
