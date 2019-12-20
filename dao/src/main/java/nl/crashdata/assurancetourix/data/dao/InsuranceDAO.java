package nl.crashdata.assurancetourix.data.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

	public boolean exists(long id)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<PInsurance> root = query.from(PInsurance.class);
		// query.select(PInsurance_.)
		return false;
	}
}
