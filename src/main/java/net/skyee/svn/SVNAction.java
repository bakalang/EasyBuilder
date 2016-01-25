
package net.skyee.svn;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.skyee.Context;
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
    Logger log = LoggerFactory.getLogger(SVNAction.class);
	private Context context;
	private final SimpleDateFormat DATETIME_REMARK = new SimpleDateFormat("yyyyMMddhhmmss");
	private final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public SVNAction() {
		this.context = Context.getInstance();
	}

	public void checkout(int projectNo, String path, boolean mainProject) throws Exception
	{
		if ( cm == null )
			return;

		File destDir = new File(context.getConfigration().getSourcePath() + path);
		SVNUpdateClient uc = cm.getUpdateClient();
		uc.setIgnoreExternals(false);
		SVNDirEntry entry = repository.info(".", -1);
		String remark = null;
		long lastVersion = entry.getRevision();
		long dbVersion = context.getProjectDAO().getProjectByNo(projectNo).getLastVersion();
        log.info("no = " +projectNo+ ", path = " + destDir.getAbsolutePath() + ",  dbVersion = " + dbVersion + ", lastVersion = " + lastVersion);
		if ( lastVersion > dbVersion )
		{
			Calendar c = Calendar.getInstance();
			remark = DATETIME_REMARK.format(c.getTime());
			// do checkout
			uc.doCheckout(repository.getLocation(), destDir, SVNRevision.create(lastVersion), SVNRevision.create(lastVersion), SVNDepth.INFINITY, true);

			// do check config changed
			if ( mainProject )
			{
				context.getProjectCheckoutDAO().insert(DATETIME.format(c.getTime()), projectNo, lastVersion, remark);

				Map<String, String> configChangedPathMap = getModifiedConfiguration(repository.log(new String[]{""}, null, dbVersion, lastVersion, true, true));
                log.info("currentVersionWithModifiedConfiguration size=" + configChangedPathMap.size());

				if ( null != remark && configChangedPathMap.size() > 0 )
				{
					for ( String mapKey : configChangedPathMap.keySet() )
					{
						context.getProjectConfigurationHstoryDAO().insert(projectNo, remark, mapKey, configChangedPathMap.get(mapKey));
					}
				}
			}

			// do update version
			context.getProjectDAO().updateProjectVersion(lastVersion, projectNo);

            log.info("checkout " + path + " done!");
			// do build
//			RunExecutor re = new RunExecutor();
//			System.out.print(", return = " + re.runScript("ant -f " + destDir.getPath() + "/build.xml -Dtoday=" + remark));
//			System.out.print(", build " + path + " done!");
		}
		return;
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
