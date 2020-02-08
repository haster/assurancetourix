package nl.crashdata.assurancetourix.data.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nl.crashdata.assurancetourix.data.entities.PInsurance;
import nl.crashdata.assurancetourix.data.entities.PInsurance_;

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
		query.where(criteriaBuilder.equal(root.get(PInsurance_.id), id));
		query.select(criteriaBuilder.count(root.get(PInsurance_.id)));
		return em.createQuery(query).getSingleResult() > 0;
	}

	public PInsurance get(long id)
	{
		return em.find(PInsurance.class, id);
	}

	public List<PInsurance> getAll()
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<PInsurance> query = criteriaBuilder.createQuery(PInsurance.class);
		query.from(PInsurance.class);
		return em.createQuery(query).getResultList();
	}

	public void delete(PInsurance pInsurance)
	{
		em.remove(pInsurance);
	}
}
