package net.praqma.codeacademy.romannumerals;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

	@Test
	public void testConvert() {
		assertEquals("I", App.convert(1));
		assertEquals("II", App.convert(2));
	}

}
