package nl.crashdata.assurancetourix.rest.resources.impl;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import nl.crashdata.assurancetourix.data.dao.InsuranceDAO;
import nl.crashdata.assurancetourix.data.entities.PInsurance;
import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Transactional
public class InsuranceResourceImpl implements InsuranceResource
{
	private static final Logger log = LoggerFactory.getLogger(InsuranceResourceImpl.class);

	@EJB
	private InsuranceDAO insuranceDAO;

	@Context
	private HttpServletRequest currentRequest;

	@Override
	public Response create(Insurance insurance)
	{
		PInsurance pInsurance = new PInsurance();
		pInsurance.setName(insurance.getName());
		pInsurance.setPolicyNumber(insurance.getPolicyNumber());

		insuranceDAO.save(pInsurance);

		URI location = UriBuilder.fromUri(HttpUtils.getRequestURL(currentRequest).toString())
			.path("/{id}")
			.build(pInsurance.getId());

		return Response.created(location).entity(insurance).build();
	}

	@Override
	public Response getAll()
	{
		log.info("getAll called");
		return null;
	}

	@Override
	public Response get(long id)
	{
		if (insuranceDAO.exists(id))
		{
			PInsurance insurance = insuranceDAO.get(id);
			Insurance restInsurance = new Insurance();
			restInsurance.setName(insurance.getName());
			restInsurance.setPolicyNumber(insurance.getPolicyNumber());
			return Response.ok(restInsurance).build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
