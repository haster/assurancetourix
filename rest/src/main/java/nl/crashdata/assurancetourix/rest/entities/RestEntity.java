package nl.crashdata.assurancetourix.rest.entities;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RestEntity
{
	@XmlElement(required = true)
	private String _type;

	private URI _self;

	private URI _related;

	public RestEntity()
	{
		super();
	}

	public URI self()
	{
		return _self;
	}

	public void self(URI _self)
	{
		this._self = _self;
	}

	public URI related()
	{
		return _related;
	}
}
