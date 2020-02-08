package nl.crashdata.assurancetourix.rest.entities;

public class Insurance extends RestEntity
{
	private String name;

	private long policyNumber;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getPolicyNumber()
	{
		return policyNumber;
	}

	public void setPolicyNumber(long policyNumber)
	{
		this.policyNumber = policyNumber;
	}
}
