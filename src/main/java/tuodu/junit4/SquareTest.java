package tuodu.junit4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SquareTest {
	private int param;
	private int result;
	private static Calculator calculator = new Calculator();

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		calculator.clear();
	}

	@Parameters
	public static Collection Data(){
		return Arrays.asList(new Object[][]{
			{-2,4},
			{3,9},
			{0,0}
		});
	}

	public SquareTest(int param,int result) {
		this.param = param;
		this.result = result;
	}
	@Test
	public void testSquare() {
		calculator.square(param);
		assertEquals(result, calculator.getResult());
	}

}
