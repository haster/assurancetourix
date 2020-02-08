package nl.crashdata.assurancetourix.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.crashdata.assurancetourix.rest.entities.Insurance;

@Path("insurance")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface InsuranceResource
{
	@GET
	public Response getAll();

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") long id);

	@POST
	public Response create(Insurance insurance);

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") long id, Insurance insurance);

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id);
}
