package nl.crashdata.assurancetourix.rest.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Insurance
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
