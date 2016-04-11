package tuodu.junit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CalculatorTest.class })
public class SuiteClass {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
	}

}
