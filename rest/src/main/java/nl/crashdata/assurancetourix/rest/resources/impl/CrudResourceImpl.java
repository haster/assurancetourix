package nl.crashdata.assurancetourix.rest.resources.impl;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import nl.crashdata.assurancetourix.data.dao.AbstractDAO;
import nl.crashdata.assurancetourix.data.entities.AbstractEntity;
import nl.crashdata.assurancetourix.rest.entities.RestEntity;
import nl.crashdata.assurancetourix.rest.resources.CrudResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CrudResourceImpl<R extends RestEntity, T extends AbstractEntity,
		D extends AbstractDAO<T>> implements CrudResource<R>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Context
	private UriInfo currentRequestUriInfo;

	protected abstract D getDao();

	@Override
	public Response create(R restEntity)
	{
		T persistentEntity = toPersistent(restEntity);

		getDao().save(persistentEntity);

		restEntity = toRest(persistentEntity);
		return Response.created(restEntity.self()).entity(restEntity).build();
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

	protected abstract R toRest(T persistentEntity);

	protected abstract T toPersistent(R restEntity);

	protected void setDefaultFields(T persistentEntity, R restEntity)
	{
		restEntity.self(toLocationUri(persistentEntity));
	}

	protected URI toLocationUri(T persistentEntity)
	{
		MultivaluedMap<String, String> pathParameters = currentRequestUriInfo.getPathParameters();

		if (pathParameters.containsKey("id"))
		{
			return currentRequestUriInfo.getAbsolutePathBuilder()
				.path("..")
				.path("{id}")
				.build(persistentEntity.getId())
				.normalize();
		}
		return currentRequestUriInfo.getAbsolutePathBuilder()
			.path("{id}")
			.build(persistentEntity.getId());
	}
}
