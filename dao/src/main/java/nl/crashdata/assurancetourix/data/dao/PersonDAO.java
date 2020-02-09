package nl.crashdata.assurancetourix.data.dao;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nl.crashdata.assurancetourix.data.entities.PPerson;

@Stateless
@Transactional(value = TxType.MANDATORY)
public class PersonDAO extends AbstractDAO<PPerson>
{

}
