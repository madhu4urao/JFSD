package klh.edu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JunitClass {
	@Test
	public void setup() {
		String str="klh";
		String str1="klh";
		String str2="edu";
		String str3="null";
		String str4="edu";
		int val=5;
		int val1=7;
		String[] expectedArray= {"one","two","three"};
		String[] resultArray= {"one","two","three"}
	    //assertEquals:checks whether two primitives or objects are same
		assertEquals(str,str1);
		//assertTrue:checks the condition is true
		assertTrue(val<val1);
		//assertFalse:checks the condition is False
		assertFalse(val<val1);
		//
	
	}
	

}
