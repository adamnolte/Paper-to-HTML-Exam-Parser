package mzon.adam.parser;

import java.io.IOException;
import java.util.ArrayList;

public class SubmitHandler {

	private String inFilePath;
	private String outFilePath;
	public SubmitHandler(String inFilePath, String outFilePath){
		this.inFilePath = inFilePath;
		this.outFilePath = outFilePath;
	}
	
	public boolean parseFile(){		
		FileParser fileParser = new FileParser(inFilePath, Main.ENCODING);
		FileWriter fileWriter = new FileWriter(outFilePath + "\\MZONParserOutput.txt", Main.ENCODING);
		try {
			fileParser.readFile();
			if(fileParser.getParsedFile() == null){
				return false;
			}
			else{
				fileWriter.writeFile(fileParser.getParsedFile());
				return true;
			}
		} 
		catch (Exception e) {
			return false;
		}
	}
}
