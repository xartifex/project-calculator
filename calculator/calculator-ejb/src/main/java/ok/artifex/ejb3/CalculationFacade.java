package ok.artifex.ejb3;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class CalculationFacade extends AbstractFacade<Calculation>
{
    @PersistenceContext(unitName = "ok.artifex_calculator-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public CalculationFacade()
    {
        super(Calculation.class);
    }
    
}
