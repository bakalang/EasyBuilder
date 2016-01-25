
package net.skyee.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@UseStringTemplate3StatementLocator
public interface ProjectConfigurationHstoryDAO
{
	@SqlUpdate("insert into project_configuration_history values (:project_no, :remark, :path, :action)")
	void insert(@Bind("project_no") int projectNo,
				@Bind("remark") String remark,
				@Bind("path") String path,
				@Bind("action") String action);

}
