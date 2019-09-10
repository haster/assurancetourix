package nl.crashdata.assurancetourix.rest.insurance;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class InsuranceResourceTest
{
	private static final URI DEPLOYMENT_URI = URI.create("http://127.0.0.1:8080");

	private ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

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
		return resteasyClient.target(DEPLOYMENT_URI)
			.path("service")
			.proxy(InsuranceResource.class)
			.create(insurance);
	}
}
