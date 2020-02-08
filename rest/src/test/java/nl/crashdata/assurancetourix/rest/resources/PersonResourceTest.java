package nl.crashdata.assurancetourix.rest.resources;

import static nl.crashdata.assurancetourix.rest.AbstractRestTest.*;

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
	}

	private PersonResource proxy()
	{
		return proxy(PersonResource.class);
	}
}
