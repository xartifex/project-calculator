/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ok.artifex.calculatorlib;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class BigDecimalCalculator implements Calculator<BigDecimal>
{

    public BigDecimal add(BigDecimal a, BigDecimal b)
    {
        return a.add(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b)
    {
        return a.divide(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b)
    {
        return a.multiply(b);
    }

    public BigDecimal subtruct(BigDecimal a, BigDecimal b)
    {
        return a.subtract(b);
    }    
}
