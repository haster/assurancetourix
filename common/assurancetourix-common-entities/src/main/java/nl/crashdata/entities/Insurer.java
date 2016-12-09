package nl.crashdata.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity(name = "Insurer")
public class Insurer extends AbstractEntity
{
	@Column
	private String name;

	@JoinColumn(name = "ADDRESS")
	private Address address;
}
