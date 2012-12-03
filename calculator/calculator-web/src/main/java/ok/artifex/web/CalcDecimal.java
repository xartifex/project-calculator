/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ok.artifex.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ok.artifex.calculatorlib.MathOperation;
import ok.artifex.ejb3.CalculateDecimal;
import ok.artifex.ejb3.Calculation;
import ok.artifex.ejb3.CalculationFacade;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "CalcDecimal", urlPatterns =
{
    "/dec"
})
public class CalcDecimal extends HttpServlet
{

    @EJB
    private CalculateDecimal calculateDecimal;
    @EJB
    private CalculationFacade calculationFacade;
    //for async logging
    @Resource(mappedName = "jms/ConnectionFactory-0")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/Queue-0")
    private Queue queue;

    private void asyncLog(Calculation calculation)
    {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try
        {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(queue);

            ObjectMessage message = session.createObjectMessage();
            message.setObject(calculation);
            producer.send(message);
        }
        catch (JMSException ex)
        {
            Logger.getLogger(FixedSum.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        //todo: artifex: question: how to make the following thing shorter?
        finally
        {
            if (producer != null)
            {
                try
                {
                    producer.close();
                }
                catch (JMSException ex)
                {
                    Logger.getLogger(FixedSum.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            }
            if (session != null)
            {
                try
                {
                    session.close();
                }
                catch (JMSException ex)
                {
                    Logger.getLogger(FixedSum.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (JMSException ex)
                {
                    Logger.getLogger(FixedSum.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private static final HashMap<String, MathOperation> opToOp = new HashMap<String, MathOperation>();
    static
    {
        opToOp.put("+", MathOperation.ADD);
        opToOp.put("/", MathOperation.DIVIDE);
        opToOp.put("*", MathOperation.MULTIPLY);
        opToOp.put("-", MathOperation.SUBTRACT);
    }
    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = input.readLine()) != null)
        {
            sb.append(inputLine);
        }
        String strResponse = sb.toString();
        Scanner scanner = new Scanner(strResponse);
        scanner.useDelimiter(";");
        Map<String, String> keyValueMap = new HashMap<String, String>();
        while (scanner.hasNext())
        {
            String keyValue = scanner.next();
            String[] keyAndValue = keyValue.split("=");
            if (keyAndValue.length > 1)
            {
                keyValueMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }

        String a = keyValueMap.get("a");
        String b = keyValueMap.get("b");
        String operation = keyValueMap.get("op");        
        
        MathOperation mathOperation = opToOp.get(operation);
        if(mathOperation == null)
        {
            throw new RuntimeException("Invalid operation supplied: " + operation);
        }
        
        BigDecimal bA = null;
        BigDecimal bB = null;

        if (a != null && b != null)
        {
            try
            {            
                bA = new BigDecimal(a);
                bB = new BigDecimal(b);
            }
            catch(NumberFormatException e)
            {                
                Logger.getLogger(FixedSum.class.getName()).log(Level.SEVERE, a + " " + b, e);
                throw new RuntimeException(e);
            }
        }

        BigDecimal result = null;
        if (bA != null && bB != null)
        {
            result = calculateDecimal.calculate(bA, bB, mathOperation);
            Calculation calculation = new Calculation();
            calculation.setA(bA);
            calculation.setB(bB);
            calculation.setOp(mathOperation.toString());
            calculation.setCalcres(result);
            calculationFacade.create(calculation);
            asyncLog(calculation);
            
            PrintWriter printWriter = new PrintWriter(response.getOutputStream());
            printWriter.print("{\"result\":" +  result.toString() + "}");
            printWriter.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "simple web calculator";
    }// </editor-fold>
}
