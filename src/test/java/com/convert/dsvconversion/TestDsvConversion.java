package com.convert.dsvconversion;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDsvConversion {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		DsvConversion conversion = new DsvConversion();
		assertTrue("Sucessfully converted",
				conversion.convert("C:\\Users\\User\\Downloads\\Java task\\DSV_input_1.txt"));
	}

}
