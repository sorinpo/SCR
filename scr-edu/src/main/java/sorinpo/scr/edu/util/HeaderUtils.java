package sorinpo.scr.edu.util;

import org.springframework.http.HttpHeaders;


public class HeaderUtils {

	public static HttpHeaders headers(){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
	}
	
	public static HttpHeaders htmlHeaders(){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        return headers;
	}
	
}
