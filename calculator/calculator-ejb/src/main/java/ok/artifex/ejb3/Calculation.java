package ok.artifex.ejb3;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
@Entity
public class Calculation implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_SEQ")
    @SequenceGenerator(name="BASE_SEQ",sequenceName="BASE_SEQ")    
    private Long id;
   
    private BigDecimal a;
    private BigDecimal b; 
    private BigDecimal calcres;
   
    private String mathOperation;

    public Calculation()
    {        
    }
    
    public BigDecimal getCalcres()
    {
        return calcres;
    }

    public void setCalcres(BigDecimal calcres)
    {
        this.calcres = calcres;
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
