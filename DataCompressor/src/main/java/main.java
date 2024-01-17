package main.java;


public class main {
	
	public static void main(String[] args) {
		
//		String text = "ababaaabba\n";
////		text="abacabab";
////		text="12131212";
//		String encodedText = LZ78.getEncodedString(text);
//		System.out.println(encodedText);//0a0b1b1a3b0a
//////		
//		String decodedText = LZ78.getDecodedString(encodedText);
//		System.out.println(decodedText);
		
		LZ78.encodeTheFile("src/main/resources/WarAndPeace.txt", "src/main/resources/encoded.bin");
		LZ78.decodeTheFile("src/main/resources/encoded.bin", "src/main/resources/decoded.bin");
	}
}
