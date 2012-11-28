package ok.artifex.ejb3;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import ok.artifex.calculatorlib.MathOperation;

/**
 *
 * @author Administrator
 */
@Entity
public class Calculation implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
    private BigDecimal a;
    private BigDecimal b; 
    private BigDecimal result;
   
    private String mathOperation;

    public Calculation()
    {        
    }
    
    public BigDecimal getResult()
    {
        return result;
    }

    public void setResult(BigDecimal result)
    {
        this.result = result;
    }

    public BigDecimal getA()
    {
        return a;
    }

    public void setA(BigDecimal a)
    {
        this.a = a;
    }

    public BigDecimal getB()
    {
        return b;
    }

    public void setB(BigDecimal b)
    {
        this.b = b;
    }

    public String getOp()
    {
        return mathOperation;
    }

    public void setOp(String mathOperation)
    {
        this.mathOperation = mathOperation;
    }        
        
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }    
        

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calculation))
        {
            return false;
        }
        Calculation other = (Calculation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ok.artifex.ejb3.MathOperation[ id=" + id + " ]";
    }
}
