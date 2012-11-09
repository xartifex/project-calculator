/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ok.artifex.calculatorlib;

/**
 *
 * @author Administrator
 */
public interface Calculator<T>
{
    T add(T a, T b);
    T subtruct(T a, T b);
    T multiply(T a, T b);
    T divide(T a, T b);
}
