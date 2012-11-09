package ok.artifex.calculatorlib;

import java.math.BigDecimal;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class BigDecimalCalculatorTest
{
    
    public BigDecimalCalculatorTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of add method, of class BigDecimalCalculator.
     */
    @Test
    public void testAdd()
    {
        System.out.println("add");
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(20);
        BigDecimalCalculator instance = new BigDecimalCalculator();
        BigDecimal expResult = new BigDecimal(30);
        BigDecimal result = instance.add(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of divide method, of class BigDecimalCalculator.
     */
    @Test
    public void testDivide()
    {
        System.out.println("divide");
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(20);
        BigDecimalCalculator instance = new BigDecimalCalculator();
        BigDecimal expResult = new BigDecimal(0.5);
        BigDecimal result = instance.divide(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of multiply method, of class BigDecimalCalculator.
     */
    @Test
    public void testMultiply()
    {
        System.out.println("multiply");
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(20);
        BigDecimalCalculator instance = new BigDecimalCalculator();
        BigDecimal expResult = new BigDecimal(200);
        BigDecimal result = instance.multiply(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtruct method, of class BigDecimalCalculator.
     */
    @Test
    public void testSubtruct()
    {
        System.out.println("subtruct");
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(20);
        BigDecimalCalculator instance = new BigDecimalCalculator();
        BigDecimal expResult = new BigDecimal(-10);
        BigDecimal result = instance.subtruct(a, b);
        assertEquals(expResult, result);     
    }
}
