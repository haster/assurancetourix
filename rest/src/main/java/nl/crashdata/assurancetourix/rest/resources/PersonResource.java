package nl.crashdata.assurancetourix.rest.resources;

import javax.ws.rs.Path;

import nl.crashdata.assurancetourix.rest.entities.Person;

@Path("person")
public interface PersonResource extends CrudResource<Person>
{

}
