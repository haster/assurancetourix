package nl.crashdata.assurancetourix.data.entities;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.PROPERTY)
public class AbstractEntity
{
	public static final int DEFAULT_NUMERIC_LENGTH = 11;

	public static final int DEFAULT_TEXT_LENGTH = 255;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Access(AccessType.FIELD)
	@Column(name = "id", nullable = false, precision = DEFAULT_NUMERIC_LENGTH)
	private Long id;

	@Version
	@Column(name = "version", nullable = true, precision = DEFAULT_NUMERIC_LENGTH)
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
