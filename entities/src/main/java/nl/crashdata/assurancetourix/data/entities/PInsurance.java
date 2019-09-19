package nl.crashdata.assurancetourix.data.entities;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "insurance")
public class PInsurance
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Access(AccessType.FIELD)
	@Column(name = "id", nullable = false)
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Basic(optional = false)
	@Column(name = "policyNumber", nullable = false, precision = 11)
	private long policyNumber;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

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
