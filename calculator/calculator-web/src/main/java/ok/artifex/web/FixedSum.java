package ok.artifex.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ok.artifex.calculatorlib.MathOperation;
import ok.artifex.ejb3.CalculateDecimal;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "FixedSum", urlPatterns =
{
    "/FixedSum"
})
public class FixedSum extends HttpServlet
{

    @EJB
    private CalculateDecimal calculateDecimal;
    
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {            
            String a = request.getParameter("a");
            String b = request.getParameter("b");
            
            BigDecimal bA = null;
            BigDecimal bB = null;
            
            if(a != null && b != null)
            {
                bA = new BigDecimal(a);
                bB = new BigDecimal(b);                
            }
            
            BigDecimal result = null;
            if(bA != null && bB != null)
            {
                result = calculateDecimal.calculate(bA, bB, MathOperation.ADD);
            }
                        
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FixedSum</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FixedSum at " + request.getContextPath() + "</h1>"); 
            
            out.println("</br>Servlet sums two numbers");             
            out.println("<form>");
            out.println("A: <input type='text' name='a' value='" + getValue(a) +"'><br/>");
            out.println("B: <input type='text' name='b' value='" + getValue(b) +"'><br/>");
            out.println("<input type='submit'><br/>");
            out.println("</form>");
            out.println("<br>result: " + result); 
            
            out.println("</body>");
            out.println("</html>");
        }
        catch(NumberFormatException e)
        {
            response.sendRedirect(request.getContextPath()+"/FixedSum");
            throw new RuntimeException(e);
        }
        finally
        {            
            out.close();
        }
    } 
    
    private String getValue(String str)
    {
        return str == null ? "" : str;
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
        return "Short description";
    }// </editor-fold>
}
