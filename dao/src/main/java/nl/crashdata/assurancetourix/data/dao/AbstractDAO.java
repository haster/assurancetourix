package nl.crashdata.assurancetourix.data.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nl.crashdata.assurancetourix.data.entities.AbstractEntity;
import nl.crashdata.assurancetourix.data.entities.AbstractEntity_;

public class AbstractDAO<T extends AbstractEntity>
{
	@PersistenceContext
	private EntityManager em;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	protected AbstractDAO()
	{
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];
	}

	public void save(T entity)
	{
		em.persist(entity);
	}

	public boolean exists(long id)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<T> root = query.from(entityClass);
		query.where(criteriaBuilder.equal(root.get(AbstractEntity_.id), id));
		query.select(criteriaBuilder.count(root.get(AbstractEntity_.id)));
		return em.createQuery(query).getSingleResult() > 0;
	}

	public T get(long id)
	{
		return em.find(entityClass, id);
	}

	public List<T> getAll()
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
		query.from(entityClass);
		return em.createQuery(query).getResultList();
	}

	public void delete(T entity)
	{
		em.remove(entity);
	}

}
