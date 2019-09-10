package nl.crashdata.assurancetourix.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("/service")
public class AssurancetourixRestApplication extends Application
{
	private static final Logger log = LoggerFactory.getLogger(AssurancetourixRestApplication.class);

	public AssurancetourixRestApplication()
	{
		log.info("Application created");
	}

	@Override
	public Set<Class< ? >> getClasses()
	{
		log.info("getClasses called");
		return super.getClasses();
	}

	@Override
	public Set<Object> getSingletons()
	{
		log.info("getSingletons called");
		return super.getSingletons();
	}
}
