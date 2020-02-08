package nl.crashdata.assurancetourix.rest.entities;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RestEntity
{
	private URI identifier;

	public RestEntity()
	{
		super();
	}

	public URI getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(URI location)
	{
		this.identifier = location;
	}

}
