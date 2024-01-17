package main.java;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;


public class LZ78 {
	

	public static String getEncodedString(String receivedText) {
		String result = null;
		
		if(receivedText.length() > 0) {
			result = encode(receivedText.trim());
		}
		else {
			System.out.println("Ничего не передано!");
		}
		
		return result;	
	}
	
	public static String getDecodedString(String encodedText) {
		String result = null;
		
		if(encodedText.length() > 0) {
			result = decode(encodedText);
		}
		else {
			System.out.println("Ничего не передано!");
		}
		
		return result;
		
	}
	
	// Кодирование
	private static String encode(String receivedText) {
		HashMap<String, Integer> dictionary = new HashMap<>(); 
		
		StringBuilder encodedText = new StringBuilder();
		
		String prefix = "";
		String symbol = "";
		
		dictionary.put(prefix, 0);
		for(int i=0; i<receivedText.length(); i++) {
			symbol = Character.toString(receivedText.charAt(i));
			if(symbol.equals("\n")||symbol.equals("\t")||symbol.equals("\r")) {
				continue;
			}
			if(dictionary.containsKey(prefix + symbol)) {
				prefix += symbol;
			}
			else {
				dictionary.put(prefix + symbol, dictionary.size());
				encodedText.append(dictionary.get(prefix) + symbol + " ");
				prefix = "";
			}
			
		}
		
		if(!prefix.equals("")) {
			encodedText.append(dictionary.get(prefix.substring(0,prefix.length()-1)) 
					+ prefix.substring(prefix.length()-1));
		}
		
		return encodedText.toString();
	}	
	
	// Декодирование
	private static String decode(String encodedText) {
		
		ArrayList<String> decodedTokens = new ArrayList<>();
		decodedTokens.add("");
		StringBuilder decodedText = new StringBuilder();
		
		Pattern pattern = Pattern.compile("\\d+.(?=\s\\d)|\\d+.");
		Matcher matcher = pattern.matcher(encodedText);
		
		while(matcher.find()) {
			int encodedPosition = Integer.parseInt(encodedText.substring(matcher.start(), matcher.end()-1));
			String newToken = encodedText.substring(matcher.end()-1, matcher.end());
			String decodedWord = decodedTokens.get(encodedPosition) + newToken;
			decodedText.append(decodedWord);
			decodedTokens.add(decodedWord);
		}
		return decodedText.toString();
	}
	
	
	public static void encodeTheFile(String pathToTheFile, String pathToTheSaveFile) {
		if(pathToTheFile.length() == 0) {
			System.out.println("Путь к сжимаемому файлу не указан!");
		}
		else if(pathToTheSaveFile.length() == 0) {
			System.out.println("Путь файла-сохранения не указан!");
		}
		else{
			String encodedText = encode(readTheFile(pathToTheFile));
			writeInFile(pathToTheSaveFile, encodedText);
		}
	}
	
	
	public static void decodeTheFile(String pathToTheZipFile, String pathToTheSaveFile) {
		if(pathToTheZipFile.length() == 0) {
			System.out.println("Путь к декодируемому файлу не указан!");
		}
		else if(pathToTheSaveFile.length() == 0) {
			System.out.println("Путь файла-сохранения не указан!");
		}
		else {
			String decodedText = decode(readTheFile(pathToTheZipFile));
			writeInFile(pathToTheSaveFile, decodedText);
		}
		
	}
	
	// Запись в файл
	private static void writeInFile(String filePath, String text) {
		try(FileOutputStream outputStream = new FileOutputStream(filePath, false)) {
			byte[] buffer = text.getBytes(StandardCharsets.UTF_8);
			outputStream.write(buffer, 0, buffer.length);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Чтение из файла
	private static String readTheFile(String filePath) {
		String readedText = "";
		try(FileInputStream inputStream = new FileInputStream(filePath)){
			byte[] buffer = inputStream.readAllBytes();
			readedText = new String(buffer, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return readedText.trim();
	}
		
}
