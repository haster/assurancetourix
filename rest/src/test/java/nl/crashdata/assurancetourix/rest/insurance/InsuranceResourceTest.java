package nl.crashdata.assurancetourix.rest.insurance;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.rest.AbstractRestTest;
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
@ChameleonTarget(value = "wildfly:16.0.0.Final:managed")
// @ChameleonTarget(value = "wildfly:16.0.0.Final:managed",
// customProperties = {@Property(name = "javaVmArguments",
// value = "-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y")}
// )
@MavenBuild(pom = "../pom.xml", module = "ear")
@DeploymentParameters(testable = false)
public class InsuranceResourceTest extends AbstractRestTest
{
	private static final long TEST_INSURANCE_POLICYNUMBER = 123456789L;

	private static final String TEST_INSURANCE_NAME = "Test insurance 1";

	private static final Insurance TEST_INSURANCE_1 =
		createInsurance(TEST_INSURANCE_NAME, TEST_INSURANCE_POLICYNUMBER);

	private static final Insurance TEST_INSURANCE_2 =
		createInsurance("Test insurance 2", 95135746L);

	private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

	@ArquillianResource
	private URL url;

	@Test
	public void create()
	{
		Response createdResponse = post(TEST_INSURANCE_1);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		Response getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		GenericType<List<Insurance>> listInsuranceType = new GenericType<>()
		{
		};
		List<Insurance> insurances = getAllResponse.readEntity(listInsuranceType);
		assertEquals(1, insurances.size());
		Insurance insuranceFromList = insurances.get(0);
		assertEquals(TEST_INSURANCE_NAME, insuranceFromList.getName());
		assertEquals(TEST_INSURANCE_POLICYNUMBER, insuranceFromList.getPolicyNumber());

		URI location = createdResponse.getLocation();
		Response getResponse = get(parseId(location));
		assertEquals(Status.OK, getResponse.getStatusInfo());

		Insurance returnedInsurance = getResponse.readEntity(Insurance.class);
		assertNotNull(returnedInsurance);
		assertEquals(TEST_INSURANCE_NAME, returnedInsurance.getName());
		assertEquals(123456789L, returnedInsurance.getPolicyNumber());
	}

	@Test
	public void createAndUpdate()
	{
		Response createdResponse = post(TEST_INSURANCE_2);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		Response getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());

		URI location = createdResponse.getLocation();
		Response getResponse = get(parseId(location));
		assertEquals(Status.OK, getResponse.getStatusInfo());

		Insurance returnedInsurance = getResponse.readEntity(Insurance.class);
		assertNotNull(returnedInsurance);
		assertEquals(TEST_INSURANCE_2.getName(), returnedInsurance.getName());
		assertEquals(TEST_INSURANCE_2.getPolicyNumber(), returnedInsurance.getPolicyNumber());

		returnedInsurance.setName("Test");
		Response putResponse = put(parseId(location), returnedInsurance);
		assertEquals(Status.NO_CONTENT, putResponse.getStatusInfo());

		Response getResponse2 = get(parseId(location));
		assertEquals(Status.OK, getResponse2.getStatusInfo());

		Insurance returnedInsurance2 = getResponse2.readEntity(Insurance.class);
		assertNotNull(returnedInsurance2);
		assertEquals("Test", returnedInsurance2.getName());
		assertEquals(TEST_INSURANCE_2.getPolicyNumber(), returnedInsurance2.getPolicyNumber());
	}

	@Test
	public void createAndDelete()
	{
		Insurance testInsurance3 = createInsurance("Test 3", 1122334455L);

		Response getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		GenericType<List<Insurance>> listInsuranceType = new GenericType<>()
		{
		};
		List<Insurance> insurances = getAllResponse.readEntity(listInsuranceType);
		int countBeforeCreation = insurances.size();

		Response createdResponse = post(testInsurance3);
		assertEquals(Status.CREATED, createdResponse.getStatusInfo());

		getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		insurances = getAllResponse.readEntity(listInsuranceType);
		int countAfterCreation = insurances.size();

		assertEquals("Count after creation should be one more than count before creation",
			countBeforeCreation + 1, countAfterCreation);

		long matchingInsuranceCount = insurances.stream()
			.filter(i -> i.getName().equals(testInsurance3.getName())
				&& i.getPolicyNumber() == testInsurance3.getPolicyNumber())
			.collect(Collectors.counting());

		assertEquals("List of all insurances should contain the created insurance exactly once", 1,
			matchingInsuranceCount);

		URI location = createdResponse.getLocation();
		Response deletedResponse = delete(parseId(location));
		assertEquals(Status.NO_CONTENT, deletedResponse.getStatusInfo());

		getAllResponse = getAll();
		assertEquals(Status.OK, getAllResponse.getStatusInfo());
		insurances = getAllResponse.readEntity(listInsuranceType);
		int countAfterDeletion = insurances.size();

		assertEquals("Count after deletion should be one less than count after creation",
			countAfterCreation - 1, countAfterDeletion);

		matchingInsuranceCount = insurances.stream()
			.filter(i -> i.getName().equals(testInsurance3.getName())
				&& i.getPolicyNumber() == testInsurance3.getPolicyNumber())
			.collect(Collectors.counting());
		assertEquals("List of all insurances shouldn't contain the deleted insurance", 0,
			matchingInsuranceCount);
	}

	@Test
	public void cantGetOrUpdateOrDeleteNonExistingInsurance()
	{
		Response getResponse = get(10);
		assertEquals(Status.NOT_FOUND, getResponse.getStatusInfo());

		Response putResponse = put(10, createInsurance("a", 1L));
		assertEquals(Status.NOT_FOUND, putResponse.getStatusInfo());

		Response deleteResponse = delete(10);
		assertEquals(Status.NOT_FOUND, deleteResponse.getStatusInfo());
	}

	private static Insurance createInsurance(String name, long policyNumber)
	{
		Insurance insurance = new Insurance();
		insurance.setName(name);
		insurance.setPolicyNumber(policyNumber);
		return insurance;
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

	private Response put(long id, Insurance insurance)
	{
		try
		{
			return resteasyClient.target(url.toURI())
				.proxy(InsuranceResource.class)
				.update(id, insurance);
		}
		catch (NullPointerException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private Response delete(long id)
	{
		try
		{
			return resteasyClient.target(url.toURI()).proxy(InsuranceResource.class).delete(id);
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
}
