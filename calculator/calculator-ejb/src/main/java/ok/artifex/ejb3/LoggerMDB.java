/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ok.artifex.ejb3;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Administrator
 */
@MessageDriven(mappedName = "jms/Queue-0", activationConfig =
{
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LoggerMDB implements MessageListener
{
    
    public LoggerMDB()
    {
    }
    
    @Override
    public void onMessage(Message message)
    {
        if (message instanceof ObjectMessage)
        {
            try
            {
                ObjectMessage msg = (ObjectMessage) message;           
                Calculation calculation = (Calculation)msg.getObject();
                Logger.getLogger(LoggerMDB.class.getName()).log(Level.INFO, 
                        "new calculation has been performed: " + calculation.getA() 
                        + " " + calculation.getOp()  + " " + calculation.getB() 
                        + " = " + calculation.getResult(), 
                        new Object[0]);
            }
            catch (JMSException ex)
            {
                Logger.getLogger(LoggerMDB.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        else
        {
            throw new IllegalStateException();
        }
    }
}
