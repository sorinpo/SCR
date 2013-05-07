package sorinpo.scr.edu;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
	private static final Logger log = LoggerFactory
			.getLogger(LogTest.class);
	private static final Logger log2 = LoggerFactory
			.getLogger(Object.class);
	
	private static final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	static {
		
		System.setOut(new PrintStream(new TeeOutputStream(System.out, out)));
		
	}
	
	@Test
	public void testSuccess(){
		
		try {
			throw new Exception("TEST");
		} catch (Exception e){
			//sorinpo logs
			log.trace("MSG", e);
			String logLines = out.toString();
			assertTrue(logLines.contains("[main] TRACE sorinpo.scr.edu.LogTest - MSG"));
			assertTrue(logLines.contains("java.lang.Exception: TEST"));
			assertTrue(logLines.contains("at sorinpo.scr.edu.LogTest.testSuccess(LogTest.java"));
			out.reset();
			
			log2.trace("MSG2");
			logLines = out.toString();
			assertTrue(logLines.length()==0);
			out.reset();
			
			log2.error("MSG3");
			logLines = out.toString();
			assertTrue(logLines.contains("[main] ERROR java.lang.Object - MSG3"));
			out.reset();
			
		}
		
	}
}
