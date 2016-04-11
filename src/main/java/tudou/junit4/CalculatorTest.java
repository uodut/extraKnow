package tudou.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CalculatorTest {
	private static Calculator calculator = new Calculator();

	@Before
	public void setUp() throws Exception {
		calculator.clear();
	}

	@Test
	public void testAdd() {
		calculator.add(2);
		calculator.add(3);
		assertEquals(5, calculator.getResult());
	}

	@Test
	public void testSubstract() {
		calculator.add(10);
		calculator.substract(2);
		assertEquals(8, calculator.getResult());
	}

	@Ignore("not complete")
	@Test
	public void testMultiply() {

	}

	@Test
	public void testDivide() {
		calculator.add(8);
		calculator.divide(2);
		assertEquals(4, calculator.getResult());
	}

	@Test(timeout = 1000)
	public void testSquareRoot() {
		calculator.squareRoot(4);
		assertEquals(2, calculator.getResult());
	}

	@Test(expected = ArithmeticException.class)
	public void devideByZero() {
		calculator.add(10);
		calculator.divide(0);
		// assertEquals(0, calculator.getResult());
	}
}
