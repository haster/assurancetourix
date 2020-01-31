package nl.crashdata.assurancetourix.rest;

import java.io.File;
import java.nio.file.Path;

import org.arquillian.container.chameleon.deployment.maven.MavenBuildAutomaticDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.embedded.BuiltProject;
import org.jboss.shrinkwrap.resolver.api.maven.embedded.EmbeddedMaven;
import org.jboss.shrinkwrap.resolver.api.maven.embedded.pom.equipped.ConfigurationDistributionStage;
import org.jboss.shrinkwrap.resolver.impl.maven.embedded.BuiltProjectImpl;

public class AbstractRestTest
{
	@Deployment
	private static Archive< ? > createDeployment()
	{
		EnterpriseArchive ear = buildEar();
		WebArchive restWar = ear.getAsType(WebArchive.class, "assurancetourix-rest.war");
		addTestClassesAndDependencies(restWar);
		ear.merge(restWar);

		return ear;
	}

	private static void addTestClassesAndDependencies(WebArchive restWar)
	{
		restWar.addPackages(true, AbstractRestTest.class.getPackage());
		JavaArchive[] testScopeArquillianDependencies = Maven.resolver()
			.loadPomFromFile("../rest/pom.xml")
			.resolve("org.arquillian.container:arquillian-chameleon-maven-build-deployment",
				"org.arquillian.container:arquillian-chameleon-junit-container-starter")
			.withTransitivity()
			.as(JavaArchive.class);
		restWar.addAsLibraries(testScopeArquillianDependencies);
	}

	/**
	 * This is a variation of what arquillian's own {@link MavenBuildAutomaticDeployment} does to
	 * build and deploy a specific (enterprise-)module of a multi-module maven project.
	 */
	private static EnterpriseArchive buildEar()
	{
		ConfigurationDistributionStage configurationDistributionStage =
			EmbeddedMaven.forProject("../pom.xml");
		configurationDistributionStage.useMaven3Version("3.6.0");

		configurationDistributionStage.setGoals("package").setQuiet(false).skipTests(true);

		BuiltProject build = configurationDistributionStage.ignoreFailure().build();

		final File projectDirectory = build.getModel().getProjectDirectory();
		final Path normalize = projectDirectory.toPath().normalize();

		final String relativeModuleDirectory = "ear".replace('/', File.separatorChar);

		build = getSubmodule(build, normalize.toString() + File.separator + relativeModuleDirectory
			+ File.separator + "pom.xml");

		return (EnterpriseArchive) build.getDefaultBuiltArchive();
	}

	private static BuiltProject getSubmodule(BuiltProject builtProject, String pomfile)
	{
		BuiltProjectImpl submodule = new BuiltProjectImpl(pomfile);
		submodule.setMavenBuildExitCode(builtProject.getMavenBuildExitCode());
		submodule.setMavenLog(builtProject.getMavenLog());
		return submodule;
	}
}
