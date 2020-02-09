package nl.crashdata.assurancetourix.rest.resources.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.data.dao.AbstractDAO;
import nl.crashdata.assurancetourix.data.entities.AbstractEntity;
import nl.crashdata.assurancetourix.rest.entities.RestEntity;
import nl.crashdata.assurancetourix.rest.resources.CrudResource;

public abstract class CrudResourceImpl<R extends RestEntity, T extends AbstractEntity,
		D extends AbstractDAO<T>> implements CrudResource<R>
{
	protected abstract D getDao();

	@Override
	public Response create(R restEntity)
	{
		T persistentEntity = toPersistent(restEntity);

		getDao().save(persistentEntity);

		restEntity = toRest(persistentEntity);
		return Response.created(restEntity.getIdentifier()).entity(restEntity).build();
	}

	@Override
	public Response getAll()
	{
		List<T> persistentEntities = getDao().getAll();
		List<R> restEntities =
			persistentEntities.stream().map(this::toRest).collect(Collectors.toList());
		GenericEntity<List<R>> genericEntity = new GenericEntity<>(restEntities)
		{
		};
		return Response.ok(genericEntity).build();
	}

	@Override
	public Response get(long id)
	{
		if (getDao().exists(id))
		{
			T persistentEntity = getDao().get(id);
			R restEntity = toRest(persistentEntity);
			return Response.ok(restEntity).build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response update(long id, R restEntity)
	{
		if (getDao().exists(id))
		{
			T persistentEntity = toPersistent(restEntity);
			persistentEntity.setId(id);
			persistentEntity = getDao().merge(persistentEntity);
			return Response.noContent().build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response delete(long id)
	{
		if (getDao().exists(id))
		{
			getDao().delete(getDao().get(id));
			return Response.noContent().build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	protected abstract R toRest(T pEntity);

	protected abstract T toPersistent(R entity);
}