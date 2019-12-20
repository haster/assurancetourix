package nl.crashdata.assurancetourix.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	public Response get(long id);

	@POST
	public Response create(Insurance insurance);
}
