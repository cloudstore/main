package co.codewizards.cloudstore.webapp.service;

import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.codewizards.cloudstore.shared.dto.ChangeSetRequest;
import co.codewizards.cloudstore.shared.dto.ChangeSetResponse;
import co.codewizards.cloudstore.shared.dto.EntityID;
import co.codewizards.cloudstore.shared.repo.local.LocalRepoRegistry;
import co.codewizards.cloudstore.shared.repo.transport.RepoTransport;
import co.codewizards.cloudstore.shared.repo.transport.RepoTransportFactory;
import co.codewizards.cloudstore.shared.repo.transport.RepoTransportFactoryRegistry;

@Path("ChangeSet")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class ChangeSetService
{
	private static final Logger logger = LoggerFactory.getLogger(ChangeSetService.class);

	{
		logger.debug("<init>: created new instance");
	}

	@POST
	@Path("{repoID}")
	public ChangeSetResponse getChangeSet(@PathParam("repoID") EntityID repoID, ChangeSetRequest request)
	{
		URL repoURL = getRepoURL(repoID);
		RepoTransportFactoryRegistry repoTransportRegistry = RepoTransportFactoryRegistry.getInstance();
		RepoTransportFactory repoTransportFactory = repoTransportRegistry.getRepoTransportFactory(repoURL);
		RepoTransport repoTransport = repoTransportFactory.createRepoTransport(repoURL);
		ChangeSetResponse response = repoTransport.getChangeSet(request);
		return response;
	}

	private URL getRepoURL(EntityID repoID) {
		LocalRepoRegistry registry = LocalRepoRegistry.getInstance();
		return null;
	}
}
