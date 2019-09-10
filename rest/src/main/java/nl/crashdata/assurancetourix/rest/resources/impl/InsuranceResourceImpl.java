package nl.crashdata.assurancetourix.rest.resources.impl;

import javax.ws.rs.core.Response;

import nl.crashdata.assurancetourix.rest.entities.Insurance;
import nl.crashdata.assurancetourix.rest.resources.InsuranceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsuranceResourceImpl implements InsuranceResource
{
	private static final Logger log = LoggerFactory.getLogger(InsuranceResourceImpl.class);

	public InsuranceResourceImpl()
	{
		log.info("resource created");
	}

	@Override
	public Response create(Insurance insurance)
	{
		log.info("create called");
		return null;
	}

	@Override
	public Response getAll()
	{
		log.info("getAll called");
		return null;
	}
}
