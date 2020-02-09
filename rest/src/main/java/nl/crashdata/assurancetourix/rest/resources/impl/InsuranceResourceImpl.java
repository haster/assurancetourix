package nl.crashdata.assurancetourix.rest.resources.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

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

	@Override
	protected InsuranceDAO getDao()
	{
		return insuranceDAO;
	}

	@Override
	protected Insurance toRest(PInsurance pEntity)
	{
		Insurance restInsurance = new Insurance();
		setDefaultFields(pEntity, restInsurance);
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
}
