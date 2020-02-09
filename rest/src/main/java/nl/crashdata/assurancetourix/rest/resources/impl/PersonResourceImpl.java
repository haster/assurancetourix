package nl.crashdata.assurancetourix.rest.resources.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nl.crashdata.assurancetourix.data.dao.PersonDAO;
import nl.crashdata.assurancetourix.data.entities.PPerson;
import nl.crashdata.assurancetourix.rest.entities.Person;
import nl.crashdata.assurancetourix.rest.resources.PersonResource;

@Stateless
@Transactional
public class PersonResourceImpl extends CrudResourceImpl<Person, PPerson, PersonDAO>
		implements PersonResource
{
	@EJB
	private PersonDAO personDAO;

	@Override
	protected PersonDAO getDao()
	{
		return personDAO;
	}

	@Override
	protected Person toRest(PPerson pEntity)
	{
		Person restPerson = new Person();
		setDefaultFields(pEntity, restPerson);
		restPerson.setFirstName(pEntity.getFirstName());
		restPerson.setLastName(pEntity.getLastName());
		restPerson.setEmailAddress(pEntity.getEmailAddress());
		return restPerson;
	}

	@Override
	protected PPerson toPersistent(Person restEntity)
	{
		PPerson persistentPerson = new PPerson();
		persistentPerson.setFirstName(restEntity.getFirstName());
		persistentPerson.setLastName(restEntity.getLastName());
		persistentPerson.setEmailAddress(restEntity.getEmailAddress());
		return persistentPerson;
	}
}
