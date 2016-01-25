package net.skyee.svn;

	

import net.skyee.bean.Login;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
	
public class SVNBase
{
	public SVNRepository repository;
	public BasicAuthenticationManager authManager;
	public SVNClientManager cm;

	public void setup(String url, Login lo) throws SVNException
	{
		DAVRepositoryFactory.setup();
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		authManager = BasicAuthenticationManager.newInstance(lo.getName(), lo.getPassword().toCharArray());
		repository.setAuthenticationManager(authManager);
		cm = SVNClientManager.newInstance();
		cm.setAuthenticationManager(authManager);
	}

	public SVNRepository getRepository()
	{
		return repository;
	}

	public BasicAuthenticationManager getAuthManager()
	{
		return authManager;
	}
}
