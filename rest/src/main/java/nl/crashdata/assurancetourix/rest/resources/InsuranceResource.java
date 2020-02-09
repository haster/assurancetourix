package nl.crashdata.assurancetourix.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.crashdata.assurancetourix.rest.entities.Insurance;

@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("insurance")
public interface InsuranceResource extends CrudResource<Insurance>
{
}
