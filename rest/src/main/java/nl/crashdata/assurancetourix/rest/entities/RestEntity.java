package nl.crashdata.assurancetourix.rest.entities;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;

public abstract class RestEntity
{
	@XmlElement(required = true)
	private String _type;

	private URI _self;

	private URI _related;

	public RestEntity()
	{
		super();
		_type = getClass().getCanonicalName();
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
