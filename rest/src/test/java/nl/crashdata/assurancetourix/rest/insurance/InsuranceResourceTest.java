package nl.crashdata.assurancetourix.rest.insurance;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@RunWith(ArquillianChameleon.class)
@ChameleonTarget("wildfly:15.0.1.Final:managed")
@MavenBuild(pom = "../pom.xml", module = "rest")
@DeploymentParameters(testable = false)
public class InsuranceResourceTest
{
	private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

	@ArquillianResource
	private URL url;

	@Test
	public void createInsurance()
	{
		Insurance insurance = new Insurance();
		insurance.setName("Test insurance");
		insurance.setPolicyNumber(123456789L);

		Response createdResponse = post(insurance);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		URI location = createdResponse.getLocation();
		Response getResponse = get(parseId(location));
		assertEquals(Status.OK, getResponse.getStatusInfo());

		Insurance returnedInsurance = (Insurance) getResponse.getEntity();
		assertNotNull(returnedInsurance);
		assertEquals(123456789L, returnedInsurance.getPolicyNumber());
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
		catch (NullPointerException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private Response getAll()
	{
		try
		{
			return resteasyClient.target(url.toURI())
				.path("service")
				.proxy(InsuranceResource.class)
				.getAll();
		}
		catch (NullPointerException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private Response get(long id)
	{
		try
		{
			return resteasyClient.target(url.toURI())
				.path("service")
				.proxy(InsuranceResource.class)
				.get(id);
		}
		catch (NullPointerException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private long parseId(URI location)
	{
		Pattern pattern = Pattern.compile("\\d{4,}");
		Matcher matcher = pattern.matcher(location.getPath());
		return Long.parseLong(matcher.group(matcher.groupCount()));
	}
}
