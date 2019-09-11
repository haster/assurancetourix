package nl.crashdata.assurancetourix.rest.insurance;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;
import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

// https://www.javacodegeeks.com/2018/03/arquillian-chameleon-simplifying-your-arquillian-tests.html
@RunWith(ArquillianChameleon.class)
@ChameleonTarget("wildfly:11.0.0.Final:managed")
@MavenBuild(pom = "../pom.xml", module = "rest")
@DeploymentParameters(testable = false)
public class InsuranceResourceTest
{
	private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

	@ArquillianResource
	URL url;

	@Test
	public void createInsurance()
	{
		Insurance insurance = new Insurance();
		insurance.setName("Test insurance");
		insurance.setPolicyNumber(123456789L);

		Response response = post(insurance);

		assertEquals(Status.CREATED, response.getStatusInfo());
	}

	private Response post(Insurance insurance)
	{
		try
		{
			return resteasyClient.target(url.toURI())
				.path("service")
				.proxy(InsuranceResource.class)
				.create(insurance);
		}
		catch (NullPointerException e)
		{
			// TODO Doe hier iets nuttigs met de exception (log.error bijv), of gooi hem door.
			throw new RuntimeException(e);
		}
		catch (URISyntaxException e)
		{
			// TODO Doe hier iets nuttigs met de exception (log.error bijv), of gooi hem door.
			throw new RuntimeException(e);
		}
	}
}
