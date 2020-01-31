package nl.crashdata.assurancetourix.rest.insurance;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.rest.AbstractRestTest;
import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;
import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget("wildfly:16.0.0.Final:managed")
@Transactional
public class InsuranceResourceTest extends AbstractRestTest
{
	private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

	@ArquillianResource
	private URL url;

	@Test
	public void create()
	{
		Insurance insurance = new Insurance();
		insurance.setName("Test insurance");
		insurance.setPolicyNumber(123456789L);

		Response createdResponse = post(insurance);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		Response getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		GenericType<List<Insurance>> listInsuranceType = listOfType(Insurance.class);
		List<Insurance> insurances = getAllResponse.readEntity(listInsuranceType);
		assertEquals(1, insurances.size());

		URI location = createdResponse.getLocation();
		Response getResponse = get(parseId(location));
		assertEquals(Status.OK, getResponse.getStatusInfo());

		Insurance returnedInsurance = getResponse.readEntity(Insurance.class);
		assertNotNull(returnedInsurance);
		assertEquals("Test insurance", returnedInsurance.getName());
		assertEquals(123456789L, returnedInsurance.getPolicyNumber());
	}

	@Test
	public void createAndUpdate()
	{
		Insurance insurance = new Insurance();
		insurance.setName("Test insurance");
		insurance.setPolicyNumber(123456789L);

		Response createdResponse = post(insurance);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		Response getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		GenericType<List<Insurance>> listInsuranceType = listOfType(Insurance.class);
		List<Insurance> insurances = getAllResponse.readEntity(listInsuranceType);
		assertEquals(1, insurances.size());

		URI location = createdResponse.getLocation();
		Response getResponse = get(parseId(location));
		assertEquals(Status.OK, getResponse.getStatusInfo());

		Insurance returnedInsurance = getResponse.readEntity(Insurance.class);
		assertNotNull(returnedInsurance);
		assertEquals("Test insurance", returnedInsurance.getName());
		assertEquals(123456789L, returnedInsurance.getPolicyNumber());
	}

	private Response post(Insurance insurance)
	{
		try
		{
			return resteasyClient.target(url.toURI())
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
			return resteasyClient.target(url.toURI()).proxy(InsuranceResource.class).getAll();
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
			return resteasyClient.target(url.toURI()).proxy(InsuranceResource.class).get(id);
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
		matcher.find();
		return Long.parseLong(matcher.group(matcher.groupCount()));
	}

	private static <T> GenericType<List<T>> listOfType(Class<T> elementType)
	{
		return new GenericType<>()
		{
		};
	}
}
