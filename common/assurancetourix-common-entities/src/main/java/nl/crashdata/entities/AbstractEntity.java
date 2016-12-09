package nl.crashdata.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", allocationSize = 100,
			initialValue = 100)
	@Column(nullable = false)
	private Long id;

	@Version
	@Column(nullable = false)
	private Long version = 0L;

	public Long getId()
	{
		return id;
	}

	public Long getVersion()
	{
		return version;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof AbstractEntity)
		{
			AbstractEntity other = (AbstractEntity) obj;
			if (getId() == null && other.getId() == null)
				return false;
			else if (getId() != null)
				return getId().equals(other.getId());
			// else fallthrough
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		if (getId() != null)
			return getId().hashCode();
		return super.hashCode();
	}
}
