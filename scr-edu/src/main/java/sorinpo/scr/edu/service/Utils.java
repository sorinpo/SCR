package sorinpo.scr.edu.service;


public class Utils {

	public static String replaceCedilles(String s) {

		if(s==null){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			switch(ch){
				case '\u015F': sb.append('\u0219');break;//s
				case '\u015E': sb.append('\u0218');break;//S
				case '\u0163': sb.append('\u021B');break;//t
				case '\u0162': sb.append('\u021A');break;//T
				default: sb.append(ch);
			}
		}
		
		return sb.toString();
	}
	
}
