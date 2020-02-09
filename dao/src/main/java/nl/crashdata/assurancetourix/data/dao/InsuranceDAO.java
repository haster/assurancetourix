package nl.crashdata.assurancetourix.data.dao;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nl.crashdata.assurancetourix.data.entities.PInsurance;

@Stateless
@Transactional(value = TxType.MANDATORY)
public class InsuranceDAO extends AbstractDAO<PInsurance>
{
}
