package nl.crashdata.assurancetourix.rest.liquibase;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

@ApplicationScoped
public class LiquibaseProducer
{
	@Resource
	private DataSource myDataSource;

	@Produces
	@LiquibaseType
	public CDILiquibaseConfig createConfig()
	{
		CDILiquibaseConfig config = new CDILiquibaseConfig();
		config.setChangeLog("changelog-master.xml");
		return config;
	}

	@Produces
	@LiquibaseType
	public DataSource createDataSource() throws SQLException
	{
		return myDataSource;
	}

	@Produces
	@LiquibaseType
	public ResourceAccessor create()
	{
		return new ClassLoaderResourceAccessor(getClass().getClassLoader());
	}

}
