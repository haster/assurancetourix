package nl.crashdata.assurancetourix.rest.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import nl.crashdata.assurancetourix.rest.entities.RestEntity;

public interface CrudResource<T extends RestEntity>
{
	@GET
	public Response getAll();

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") long id);

	@POST
	public Response create(T entity);

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") long id, T entity);

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id);
}
