package nl.crashdata.assurancetourix.data.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class PPerson extends AbstractEntity
{
	@Basic(optional = false)
	@Column(name = "firstName", nullable = false, length = DEFAULT_TEXT_LENGTH)
	private String firstName;

	@Basic(optional = false)
	@Column(name = "lastName", nullable = false, length = DEFAULT_TEXT_LENGTH)
	private String lastName;

	@Basic(optional = false)
	@Column(name = "emailAddress", nullable = false, length = DEFAULT_TEXT_LENGTH)
	private String emailAddress;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
}
