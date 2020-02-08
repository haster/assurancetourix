package nl.crashdata.assurancetourix.rest.resources.impl;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import nl.crashdata.assurancetourix.data.dao.InsuranceDAO;
import nl.crashdata.assurancetourix.data.entities.PInsurance;
import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;

@Stateless
@Transactional
public class InsuranceResourceImpl implements InsuranceResource
{
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

		URI location = UriBuilder.fromUri(currentRequest.getRequestURI())
			.path("/{id}")
			.build(pInsurance.getId());

		return Response.created(location).entity(insurance).build();
	}

	@Override
	public Response getAll()
	{
		List<PInsurance> insurances = insuranceDAO.getAll();
		List<Insurance> restInsurances =
			insurances.stream().map(this::toRestInsurance).collect(Collectors.toList());
		GenericEntity<List<Insurance>> genericEntity = new GenericEntity<>(restInsurances)
		{
		};
		return Response.ok(genericEntity).build();
	}

	@Override
	public Response get(long id)
	{
		if (insuranceDAO.exists(id))
		{
			PInsurance insurance = insuranceDAO.get(id);
			Insurance restInsurance = toRestInsurance(insurance);
			return Response.ok(restInsurance).build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response update(long id, Insurance insurance)
	{
		if (insuranceDAO.exists(id))
		{
			PInsurance pInsurance = insuranceDAO.get(id);
			pInsurance.setName(insurance.getName());
			pInsurance.setPolicyNumber(insurance.getPolicyNumber());
			insuranceDAO.save(pInsurance);
			return Response.noContent().build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response delete(long id)
	{
		if (insuranceDAO.exists(id))
		{
			insuranceDAO.delete(insuranceDAO.get(id));
			return Response.noContent().build();
		}
		else
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	private Insurance toRestInsurance(PInsurance insurance)
	{
		Insurance restInsurance = new Insurance();
		restInsurance.setName(insurance.getName());
		restInsurance.setPolicyNumber(insurance.getPolicyNumber());
		return restInsurance;
	}
}
