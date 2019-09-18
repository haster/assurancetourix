package nl.crashdata.assurancetourix.rest.resources.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nl.crashdata.assurancetourix.data.entities.PInsurance;

@Stateless
@Transactional(value = TxType.MANDATORY)
public class InsuranceDAO
{
	@PersistenceContext
	private EntityManager em;

	public void save(PInsurance pInsurance)
	{
		em.persist(pInsurance);
	}
}
