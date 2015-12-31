package mzon.adam.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileParser {

	private String filePath = "";
	private ArrayList<String> parsedFile;
	private char[] alphabet;
	private Charset ENCODING;
	private int aLineNum = 0;
	private int currQuestion = 1;
	
	FileParser(String path, Charset ENCODING){
		this.filePath = path;
		this.ENCODING = ENCODING;
		parsedFile = new ArrayList<String>();
		buildAlphabet();
	}
	
	public ArrayList<String> getParsedFile(){
		return this.parsedFile;
	}
	
	public void readFile() throws IOException{
		Path path = Paths.get(filePath);
	    try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	    	  formatLine(line);
	      }      
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private void formatLine(String line){
		String[] words = line.split("\\t");
		String formattedLine = "";
		if(line.isEmpty() || line == null){
			if(aLineNum > 1){
				++currQuestion;
				aLineNum = 0;
				parsedFile.add("<p>");
				parsedFile.add("");
				return;
			}
			else{
				return;
			}
		}
		//Check for lines that aren't questions after initial question or are special formatting chars
		else if(aLineNum >= 1 && words.length != 2){
			aLineNum = 0;
			++currQuestion;
			parsedFile.add("<p>");
			parsedFile.add("");
			return;
		}
		else if(aLineNum >= 1 && words.length == 2){
			try{
				//No Blank Line Between questions
				if((currQuestion + 1) == Integer.parseInt(words[0].replaceAll("[^0-9]", ""))){
					aLineNum = 0;
					++currQuestion;
					parsedFile.add("<p>");
					parsedFile.add("");
					++aLineNum;
					parsedFile.add("");
					parsedFile.add("Question: " + currQuestion);
					parsedFile.add("");
					parsedFile.add(boldNot(words[1]) + "<p>");
					return;
				}
				
			}
			catch(Exception e){}
		}
		//Check if its the actual question
		if(aLineNum == 0){		
			//Valid question I.E. split into two and first word is a number
			if(words.length == 2 && words[0].matches(".*\\d+.*")){
				++aLineNum;
				parsedFile.add("");
				parsedFile.add("Question: " + currQuestion);
				parsedFile.add("");
				parsedFile.add(boldNot(words[1]) + "<p>");
				return;
			}
			else{
				return;
			}
		}
		else if(aLineNum == 1){
			formattedLine = "<input type=\"radio\" name=\"q" + currQuestion + "\" value=\"" + alphabet[(aLineNum - 1)] + "\">" + line;
		}
		else{
			formattedLine = "<br /><input type=\"radio\" name=\"q" + currQuestion + "\" value=\"" + alphabet[(aLineNum - 1)] + "\">" + line;
		}
		
		++aLineNum;
		parsedFile.add(formattedLine);
		return;
	}
	
	private void buildAlphabet(){
		alphabet = "ABCDEFGHIJKLMNOPQRSTUV".toCharArray();
	}
	
	private String boldNot(String line){
		StringBuilder output = new StringBuilder();
		String[] words = line.split("\\s+");
		for(int i = 0; i < words.length; ++i){
			if(words[i].equalsIgnoreCase("not")){
				output.append("<b>" + words[i] + "</b> ");
			}
			else if(i < words.length - 1){
				output.append(words[i] + " ");
			}
			else{
				output.append(words[i]);
			}
		}
		return output.toString();
	}
}
