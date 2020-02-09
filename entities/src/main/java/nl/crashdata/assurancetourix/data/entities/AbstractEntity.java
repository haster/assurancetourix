package nl.crashdata.assurancetourix.data.entities;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.PROPERTY)
public class AbstractEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Access(AccessType.FIELD)
	@Column(name = "id", nullable = false)
	private Long id;

	@Version
	@Column(name = "version", nullable = true)
	private Long version;

	public AbstractEntity()
	{
		super();
	}

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

}
