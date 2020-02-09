package nl.crashdata.assurancetourix.data.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "insurance")
public class PInsurance extends AbstractEntity
{
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Basic(optional = false)
	@Column(name = "policyNumber", nullable = false, precision = 11)
	private long policyNumber;

	public long getPolicyNumber()
	{
		return policyNumber;
	}

	public void setPolicyNumber(long policyNumber)
	{
		this.policyNumber = policyNumber;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
