
package net.skyee.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@UseStringTemplate3StatementLocator
public interface ProjectCheckoutDAO
{
	@SqlUpdate("insert into project_checkout values (:project_no, :uuid, :version, :remark)")
	void insert(@Bind("project_no") int projectNo,
				@Bind("uuid") String uuid,
				@Bind("version") long version,
				@Bind("remark") String remark);

}
