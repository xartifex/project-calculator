/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ok.artifex.ejb3;

import java.math.BigDecimal;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import ok.artifex.calculatorlib.BigDecimalCalculator;
import ok.artifex.calculatorlib.Calculator;
import ok.artifex.calculatorlib.MathOperation;

/**
 *
 * @author Administrator
 */
@Stateless
@LocalBean
public class CalculateDecimal
{
    private final Calculator<BigDecimal> calculator;
    
    public CalculateDecimal()
    {
        calculator = new BigDecimalCalculator();
    }
    
    public BigDecimal calculate(BigDecimal a, BigDecimal b, MathOperation operation)
    {                
        return getResult(a, b, operation);
    }
    
    private BigDecimal getResult(BigDecimal a, BigDecimal b , MathOperation operation)
    {
        switch(operation)
        {
            case ADD:
            {
                return calculator.add(a, b);
            }
            case SUBTRACT:
            {
                return calculator.subtruct(a, b);
            }
            case MULTIPLY:
            {
                return calculator.multiply(a, b);
            }
            case DIVIDE:
            {
                return calculator.divide(a, b);
            }
            default:
            {
                throw new IllegalStateException();
            }
        }
    }
}
