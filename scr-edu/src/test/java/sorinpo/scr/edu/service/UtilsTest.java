package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void replaceCedillesSuccess(){
		assertEquals("ABCșțȘȚtest",Utils.replaceCedilles("ABCșțȘȚtest"));
	}
	
	@Test
	public void replaceCedillesUnchanged(){
		assertEquals("ABCșțȘȚtest",Utils.replaceCedilles("ABCşţŞŢtest"));
	}
	
	@Test
	public void replaceCedillesNulll(){
		assertNull(Utils.replaceCedilles(null));
	}
}
