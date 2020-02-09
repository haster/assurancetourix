package nl.crashdata.assurancetourix.rest.resources.impl;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;

import nl.crashdata.assurancetourix.data.dao.InsuranceDAO;
import nl.crashdata.assurancetourix.data.entities.PInsurance;
import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;

@Stateless
@Transactional
public class InsuranceResourceImpl extends CrudResourceImpl<Insurance, PInsurance, InsuranceDAO>
		implements InsuranceResource
{
	@EJB
	private InsuranceDAO insuranceDAO;

	@Context
	private HttpServletRequest currentRequest;

	@Override
	protected InsuranceDAO getDao()
	{
		return insuranceDAO;
	}

	@Override
	protected Insurance toRest(PInsurance pEntity)
	{
		Insurance restInsurance = new Insurance();
		URI toLocationUri = toLocationUri(pEntity);
		restInsurance.setIdentifier(toLocationUri);
		restInsurance.setName(pEntity.getName());
		restInsurance.setPolicyNumber(pEntity.getPolicyNumber());
		return restInsurance;
	}

	@Override
	protected PInsurance toPersistent(Insurance entity)
	{
		PInsurance pInsurance = new PInsurance();
		pInsurance.setName(entity.getName());
		pInsurance.setPolicyNumber(entity.getPolicyNumber());
		return pInsurance;
	}

	private URI toLocationUri(PInsurance insurance)
	{
		return UriBuilder.fromUri(currentRequest.getRequestURI())
			.path("/{id}")
			.build(insurance.getId());
	}
}
