
package net.skyee.svn;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.skyee.Context;
import net.skyee.bean.Component;
import net.skyee.bean.Config;
import net.skyee.build.RunExecutor;
import net.skyee.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;



public class SVNAction extends SVNBase
{
	private Logger log = LoggerFactory.getLogger(SVNAction.class);
	private Context context;
	public SVNAction() {
		this.context = Context.getInstance();
	}

	public Component checkout(int projectNo, String path, boolean mainProject, String remark) throws SVNException, IOException, SQLException, ClassNotFoundException// throws Exception
	{
		if ( cm == null )
			return null;

		Component component = null;
		File destDir = new File(context.getConfigration().getSourcePath() + path);
		SVNUpdateClient uc = cm.getUpdateClient();
		uc.setIgnoreExternals(false);
		SVNDirEntry entry = repository.info(".", -1);
		long checkoutVersion = entry.getRevision();
		long dbVersion = context.getProjectDAO().getProjectByNo(projectNo).getLastVersion();
        log.info("no = " +projectNo+ ", path = " + destDir.getAbsolutePath() + ",  dbVersion = " + dbVersion + ", lastVersion = " + checkoutVersion);

		if ( checkoutVersion > dbVersion || !destDir.exists())
		{
			// do checkout
			uc.doCheckout(repository.getLocation(), destDir, SVNRevision.create(checkoutVersion), SVNRevision.create(checkoutVersion), SVNDepth.INFINITY, true);
			component = new Component(path, checkoutVersion ,true);

			// modified configuration
			List<Config> configList = new ArrayList<Config>();
			Map<String, String> configChangedPathMap = getModifiedConfiguration(repository.log(new String[]{""}, null, dbVersion, checkoutVersion, true, true));
			log.info("currentVersionWithModifiedConfiguration size=" + configChangedPathMap.size());
			if ( null != remark && configChangedPathMap.size() > 0 )
			{
				for ( String mapKey : configChangedPathMap.keySet() )
				{
					context.getProjectConfigurationHstoryDAO().insert(projectNo, remark, mapKey, configChangedPathMap.get(mapKey));
					configList.add(new Config(mapKey, configChangedPathMap.get(mapKey)));
					component.addConfigList(new Config(mapKey, configChangedPathMap.get(mapKey)));
				}
				component.setConfigList(configList);
			}

			// do check config changed
			if ( mainProject )
			{
				String uuid = UUIDUtils.newUUID();
				context.getProjectCheckoutDAO().insert(projectNo, uuid, checkoutVersion, remark);
				component.setToken(uuid);
			}

			// do update version
			context.getProjectDAO().updateProjectVersion(checkoutVersion, projectNo);

            log.info("checkout " + path + " done!");
			// do build
			if(isAntBuild()){
				RunExecutor re = new RunExecutor();
				re.runScript("ant -f " + destDir.getPath() + "/build.xml -Dremark=" + remark+" -DzipPath="+context.getConfigration().getZipPath());
			}

		}
		return component;
	}

	@SuppressWarnings("rawtypes")
	public void checkHistory() throws SVNException
	{

		SVNDirEntry entry = repository.info(".", -1);
		long lastVersion = entry.getRevision();

		Collection logEntries = repository.log(new String[]{""}, null, lastVersion, lastVersion, true, true);

		for ( Iterator entries = logEntries.iterator(); entries.hasNext(); )
		{
			SVNLogEntry logEntry = (SVNLogEntry)entries.next();
			System.out.println("---------------------------------------------");
			System.out.println("revision: " + logEntry.getRevision());
			System.out.println("author: " + logEntry.getAuthor());
			System.out.println("date: " + logEntry.getDate());
			System.out.println("log message: " + logEntry.getMessage());

			if ( logEntry.getChangedPaths().size() > 0 )
			{
				System.out.println();
				System.out.println("changed paths:");
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for ( Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); )
				{
					SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths().get(changedPaths.next());
					System.out
							.println(" "
									+ entryPath.getType()
									+ " "
									+ entryPath.getPath()
									+ ( ( entryPath.getCopyPath() != null )? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision()
											+ ")": "" ));
				}
			}
		}
		return;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getModifiedConfiguration(Collection logEntries) throws SVNException, SQLException, IOException
	{
		Map<String, String> changedPathMap = new HashMap<String, String>();
		for ( Iterator entries = logEntries.iterator(); entries.hasNext(); )
		{
			SVNLogEntry logEntry = (SVNLogEntry)entries.next();

			if ( logEntry.getChangedPaths().size() > 0 )
			{
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for ( Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); )
				{
					SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths().get(changedPaths.next());
					if ( entryPath.getPath().contains(context.getConfigration().getConfigPath()) )
						changedPathMap.put(entryPath.getPath(), String.valueOf(entryPath.getType()));
				}
			}
		}
		return changedPathMap;
	}
}
