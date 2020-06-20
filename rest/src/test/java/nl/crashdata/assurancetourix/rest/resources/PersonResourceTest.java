package nl.crashdata.assurancetourix.rest.resources;

import static nl.crashdata.assurancetourix.rest.AbstractRestTest.*;
import static org.junit.Assert.*;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.rest.AbstractRestTest;
import nl.crashdata.assurancetourix.rest.entities.Person;
import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value = CHAMELEON_TARGET)
// @ChameleonTarget(value = CHAMELEON_TARGET,
// customProperties = {@Property(name = "javaVmArguments",
// value = "-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y")}
// )
@MavenBuild(pom = "../pom.xml", module = "ear")
@DeploymentParameters(testable = false)
public class PersonResourceTest extends AbstractRestTest
{
	@Test
	public void createPerson()
	{
		Person person = new Person();
		person.setFirstName("Tinus");
		person.setLastName("Test");
		person.setEmailAddress("t.test@example.nl");

		Response createdResponse = proxy().create(person);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		URI location = createdResponse.getLocation();
		person = createdResponse.readEntity(Person.class);
		assertEquals("Self of a freshly created restentity should be equal to its location",
			location, person.self());
	}

	private PersonResource proxy()
	{
		return proxy(PersonResource.class);
	}
}
